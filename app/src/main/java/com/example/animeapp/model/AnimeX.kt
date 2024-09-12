package com.example.animeapp.model


import com.google.gson.annotations.SerializedName

data class AnimeX(
    @SerializedName("anime")
    val anime: AnimeXX? = AnimeXX(),
    @SerializedName("role")
    val role: String? = ""
)