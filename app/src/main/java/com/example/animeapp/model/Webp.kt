package com.example.animeapp.model


import com.google.gson.annotations.SerializedName

data class Webp(
    @SerializedName("image_url")
    val imageUrl: String? = null,
    @SerializedName("large_image_url")
    val largeImageUrl: String? = null,
    @SerializedName("small_image_url")
    val smallImageUrl: String? = null
)