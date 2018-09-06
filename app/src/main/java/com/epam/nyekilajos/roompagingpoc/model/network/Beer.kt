package com.epam.nyekilajos.roompagingpoc.model.network

import com.google.gson.annotations.SerializedName

data class Beer(

        @SerializedName("id")
        val id: Int?,

        @SerializedName("name")
        val name: String?,

        @SerializedName("tagline")
        val tagline: String?,

        @SerializedName("first_brewed")
        val firstBrewed: String?,

        @SerializedName("description")
        val description: String?,

        @SerializedName("image_url")
        val imageUrl: String?,

        @SerializedName("abv")
        val abv: Double?,

        @SerializedName("ibu")
        val ibu: Double?,

        @SerializedName("target_fg")
        val targetFg: Double?,

        @SerializedName("target_og")
        val targetOg: Double?,

        @SerializedName("ebc")
        val ebc: Double?,

        @SerializedName("srm")
        val srm: Double?,

        @SerializedName("ph")
        val ph: Double?,

        @SerializedName("attenuation_level")
        val attenuationLevel: Double?,

        @SerializedName("volume")
        val volume: Amount?,

        @SerializedName("boil_volume")
        val boilVolume: Amount?,

        @SerializedName("method")
        val method: Method?,

        @SerializedName("ingredients")
        val ingredients: Ingredients?,

        @SerializedName("food_pairing")
        val foodPairing: List<String?>?,

        @SerializedName("brewers_tips")
        val brewersTips: String?,

        @SerializedName("contributed_by")
        val contributedBy: String?
)
