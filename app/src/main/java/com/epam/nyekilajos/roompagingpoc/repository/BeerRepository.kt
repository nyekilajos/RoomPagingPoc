package com.epam.nyekilajos.roompagingpoc.repository

import com.epam.nyekilajos.roompagingpoc.model.database.Beer
import com.epam.nyekilajos.roompagingpoc.model.network.BeerService
import com.epam.nyekilajos.roompagingpoc.model.network.BeerServiceException
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.reactivestreams.Publisher
import javax.inject.Inject

class BeerRepository @Inject constructor(private val beerService: BeerService) {

    fun getBeers(): Publisher<List<Beer>> = Flowable
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
            .observeOn(AndroidSchedulers.mainThread())
}
