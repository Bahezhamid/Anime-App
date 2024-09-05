package com.example.animeapp.model


import com.google.gson.annotations.SerializedName

data class Tiny(
    @SerializedName("height")
    val height: Int? = null,
    @SerializedName("width")
    val width: Int? = null
)