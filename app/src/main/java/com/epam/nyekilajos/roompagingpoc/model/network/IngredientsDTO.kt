package com.epam.nyekilajos.roompagingpoc.model.network

import com.google.gson.annotations.SerializedName

data class IngredientsDTO(

        @SerializedName("malt")
        val malt: List<MaltDTO?>?,

        @SerializedName("hops")
        val hops: List<Hop?>?,

        @SerializedName("yeast")
        val yeast: String?
)
