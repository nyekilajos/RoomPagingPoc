package com.epam.nyekilajos.roompagingpoc.model.network

import com.google.gson.annotations.SerializedName

data class Hop(

        @SerializedName("name")
        val name: String?,

        @SerializedName("amount")
        val amount: Amount?,

        @SerializedName("add")
        val add: String?,

        @SerializedName("attribute")
        val attribute: String?
)
