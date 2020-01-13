package com.epam.nyekilajos.roompagingpoc.model.network

import retrofit2.http.GET

interface BeerService {

    @GET("beers")
    suspend fun getBeers(): List<BeerDTO>
}

class BeerServiceException(message: String) : RuntimeException(message)
