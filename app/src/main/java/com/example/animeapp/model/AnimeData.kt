package com.example.animeapp.model


import com.google.gson.annotations.SerializedName

data class AnimeData(
    @SerializedName("data")
    val data: MutableList<Data?> = mutableListOf(),
    @SerializedName("pagination")
    val pagination: Pagination? = Pagination()
)