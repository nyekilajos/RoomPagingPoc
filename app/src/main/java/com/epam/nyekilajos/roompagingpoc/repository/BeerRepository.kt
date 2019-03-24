package com.epam.nyekilajos.roompagingpoc.repository

import android.util.Log
import com.epam.nyekilajos.roompagingpoc.model.database.Beer
import com.epam.nyekilajos.roompagingpoc.model.database.BeersDatabase
import com.epam.nyekilajos.roompagingpoc.model.database.Ingredients
import com.epam.nyekilajos.roompagingpoc.model.network.BeerDTO
import com.epam.nyekilajos.roompagingpoc.model.network.BeerService
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class BeerRepository @Inject constructor(private val beerService: BeerService, private val beersDatabase: BeersDatabase) {

    fun getBeers(): Flowable<List<Beer>> {
        val disposable = beersDatabase.beersDao()
                .getCount()
                .subscribeOn(Schedulers.io())
                .flatMapCompletable {
                    if (it == 0) {
                        refreshBeers()
                    } else {
                        Completable.complete()
                    }
                }
                .subscribeBy(onError = { Log.e(BeerRepository::class.java.simpleName, it.localizedMessage) })

        return beersDatabase.beersDao()
                .getBeers()
                .doFinally { disposable.dispose() }
    }

    fun refreshBeers(): Completable {
        return beerService.getBeers()
                .doOnSuccess { updateDatabase(it) }
                .ignoreElement()
    }

    private fun updateDatabase(it: List<BeerDTO>) {
        beersDatabase
                .beersDao()
                .updateAll(
                        it.mapNotNull { it.toBeer() }
                )
    }
}

fun BeerDTO.toBeer(): Beer? {
    return if (id != null && name != null) {
        Beer(
                id,
                name,
                Ingredients(
                        ingredients?.malt
                                ?.filter { malt -> malt?.name != null }
                                ?.map { malt ->
                                    malt?.name!!
                                } ?: listOf(),
                        ingredients?.hops
                                ?.filter { hop -> hop?.name != null }
                                ?.map { hop ->
                                    hop?.name!!
                                } ?: listOf(),
                        ingredients?.yeast ?: "")
        )
    } else {
        null
    }
}
