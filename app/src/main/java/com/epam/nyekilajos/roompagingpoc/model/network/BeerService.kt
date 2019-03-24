package com.epam.nyekilajos.roompagingpoc.model.network

import io.reactivex.Single
import retrofit2.http.GET

interface BeerService {

    @GET("beers")
    fun getBeers(): Single<List<BeerDTO>>
}

class BeerServiceException(message: String) : RuntimeException(message)
