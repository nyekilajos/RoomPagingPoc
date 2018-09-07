package com.epam.nyekilajos.roompagingpoc.repository

import com.epam.nyekilajos.roompagingpoc.model.database.Beer
import com.epam.nyekilajos.roompagingpoc.model.database.BeersDatabase
import com.epam.nyekilajos.roompagingpoc.model.network.BeerService
import com.epam.nyekilajos.roompagingpoc.model.network.BeerServiceException
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.reactivestreams.Publisher
import javax.inject.Inject

class BeerRepository @Inject constructor(private val beerService: BeerService, private val beersDatabase: BeersDatabase) {

    fun getBeers(): Flowable<List<Beer>> {
        beersDatabase.beersDao()
                .getCount()
                .subscribeOn(Schedulers.io())
                .flatMapCompletable {
                    if (it == 0) {
                        refreshBeers()
                    } else {
                        Completable.complete()
                    }
                }
                .subscribe()

        return beersDatabase.beersDao()
                .getBeers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun refreshBeers(): Completable {
        return Single
                .fromCallable {
                    beerService.getBeers().execute().run {
                        body()
                                ?.filter { it.id != null && it.name != null }
                                ?.map { Beer(it.id!!, it.name!!) }
                                ?: throw BeerServiceException(errorBody()?.string()
                                        ?: "Unknown error")
                    }
                }
                .subscribeOn(Schedulers.io())
                .doOnSuccess {
                    beersDatabase.beersDao().updateAll(it)
                }
                .ignoreElement()
    }
}
