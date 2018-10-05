package com.epam.nyekilajos.roompagingpoc.repository

import android.util.Log
import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import com.epam.nyekilajos.roompagingpoc.model.database.Beer
import com.epam.nyekilajos.roompagingpoc.model.database.BeersDatabase
import com.epam.nyekilajos.roompagingpoc.model.database.Ingredients
import com.epam.nyekilajos.roompagingpoc.model.network.BeerService
import com.epam.nyekilajos.roompagingpoc.model.network.BeerServiceException
import io.reactivex.BackpressureStrategy
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class BeerRepository @Inject constructor(private val beerService: BeerService, private val beersDatabase: BeersDatabase) {

    fun getBeers(): Flowable<PagedList<Beer>> {
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

        return RxPagedListBuilder(beersDatabase.beersDao().getBeers(), PAGED_LIST_CONFIG)
                .buildFlowable(BackpressureStrategy.LATEST)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally { disposable.dispose() }


    }

    fun refreshBeers(): Completable {
        return Single
                .fromCallable {
                    beerService.getBeers().execute().run {
                        body()
                                ?.filter { it.id != null && it.name != null }
                                ?.map {
                                    Beer(
                                            it.id!!,
                                            it.name!!,
                                            Ingredients(
                                                    it.ingredients?.malt
                                                            ?.filter { malt -> malt?.name != null }
                                                            ?.map { malt ->
                                                                malt?.name!!
                                                            } ?: listOf(),
                                                    it.ingredients?.hops
                                                            ?.filter { hop -> hop?.name != null }
                                                            ?.map { hop ->
                                                                hop?.name!!
                                                            } ?: listOf(),
                                                    it.ingredients?.yeast ?: "")
                                    )
                                }
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

private val PAGED_LIST_CONFIG = PagedList.Config.Builder()
        .setPrefetchDistance(10)
        .setPageSize(5)
        .setEnablePlaceholders(false)
        .setInitialLoadSizeHint(10)
        .build()
