package com.example.animeapp.model


import com.google.gson.annotations.SerializedName

data class AnimeCharacters(
    @SerializedName("links")
    val links: Links? = Links()
)