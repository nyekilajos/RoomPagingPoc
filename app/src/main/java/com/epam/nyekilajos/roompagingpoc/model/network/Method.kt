package com.epam.nyekilajos.roompagingpoc.model.network

import com.google.gson.annotations.SerializedName

data class Method(

        @SerializedName("mash_temp")
        val mashTemp: List<MashTemp?>?,

        @SerializedName("fermentation")
        val fermentation: Fermentation?,

        @SerializedName("twist")
        val twist: Any?
)
