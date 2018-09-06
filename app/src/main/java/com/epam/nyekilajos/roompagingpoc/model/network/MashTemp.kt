package com.epam.nyekilajos.roompagingpoc.model.network

import com.google.gson.annotations.SerializedName

data class MashTemp(

        @SerializedName("temp")
        val temp: Amount?,

        @SerializedName("duration")
        val duration: Int?
)
