package com.example.animeapp.model


import com.google.gson.annotations.SerializedName

data class Images(
    @SerializedName("jpg")
    val jpg: Jpg? = null,
    @SerializedName("webp")
    val webp: Webp? = null
)