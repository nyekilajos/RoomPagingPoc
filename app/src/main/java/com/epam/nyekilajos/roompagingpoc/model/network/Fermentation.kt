package com.epam.nyekilajos.roompagingpoc.model.network

import com.google.gson.annotations.SerializedName

data class Fermentation(

        @SerializedName("temp")
        val temp: Amount?
)
