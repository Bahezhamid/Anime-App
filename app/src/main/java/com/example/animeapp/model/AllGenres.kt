package com.example.animeapp.model


import com.google.gson.annotations.SerializedName

data class AllGenres(
    @SerializedName("data")
    val `data`: List<Data>? = listOf()
)