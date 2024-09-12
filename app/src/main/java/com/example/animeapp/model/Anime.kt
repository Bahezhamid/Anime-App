package com.example.animeapp.model


import com.google.gson.annotations.SerializedName

data class Anime(
    @SerializedName("anime")
    val anime: AnimeX? = AnimeX(),
    @SerializedName("role")
    val role: String? = ""
)