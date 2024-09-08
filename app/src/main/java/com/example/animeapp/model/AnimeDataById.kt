package com.example.animeapp.model


import com.google.gson.annotations.SerializedName

data class AnimeDataById(
    @SerializedName("data")
    val `data`: Data? = Data()
)