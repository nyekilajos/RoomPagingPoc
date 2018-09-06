package com.epam.nyekilajos.roompagingpoc.model.network

import com.google.gson.annotations.SerializedName

data class Ingredients(

        @SerializedName("malt")
        val malt: List<Malt?>?,

        @SerializedName("hops")
        val hops: List<Hop?>?,

        @SerializedName("yeast")
        val yeast: String?
)
