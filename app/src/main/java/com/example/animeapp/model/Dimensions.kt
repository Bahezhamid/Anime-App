package com.example.animeapp.model


import com.google.gson.annotations.SerializedName

data class Dimensions(
    @SerializedName("large")
    val large: Large? = null,
    @SerializedName("small")
    val small: Small? = null,
    @SerializedName("tiny")
    val tiny: Tiny? = null,
    @SerializedName("medium")
    val medium: Medium? = Medium(),
)