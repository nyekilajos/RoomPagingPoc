package com.epam.nyekilajos.roompagingpoc.model.network

import retrofit2.Call
import retrofit2.http.GET

interface BeerService {

    @GET("beers")
    fun getBeers(): Call<List<BeerDTO>>
}

class BeerServiceException(message: String) : RuntimeException(message)
