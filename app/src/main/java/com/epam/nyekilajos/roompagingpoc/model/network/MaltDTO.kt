package com.epam.nyekilajos.roompagingpoc.model.network

import com.google.gson.annotations.SerializedName

data class MaltDTO(

        @SerializedName("name")
        val name: String?,

        @SerializedName("amount")
        val amount: Amount?
)
