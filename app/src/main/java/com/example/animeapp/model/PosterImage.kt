package com.example.animeapp.model


import com.google.gson.annotations.SerializedName

data class PosterImage(
    @SerializedName("large")
    val large: String? = "",
    @SerializedName("medium")
    val medium: String? = "",
    @SerializedName("meta")
    val meta: Meta? = Meta(),
    @SerializedName("original")
    val original: String? = "",
    @SerializedName("small")
    val small: String? = "",
    @SerializedName("tiny")
    val tiny: String? = ""
)