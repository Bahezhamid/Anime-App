package com.example.animeapp.model


import com.google.gson.annotations.SerializedName

data class CoverImage(
    @SerializedName("large")
    val large: String? = null,
    @SerializedName("meta")
    val meta: Meta? = null,
    @SerializedName("original")
    val original: String? = null,
    @SerializedName("small")
    val small: String? = null,
    @SerializedName("tiny")
    val tiny: String? = null
)