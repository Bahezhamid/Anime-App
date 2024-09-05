package com.example.animeapp.model


import com.google.gson.annotations.SerializedName

data class AnimeData(
    @SerializedName("data")
    val data: List<Data>? = listOf(),
    @SerializedName("links")
    val links: LinksXXXXXXXXXXXXXXXXX? = LinksXXXXXXXXXXXXXXXXX(),
    @SerializedName("meta")
    val meta: Meta? = Meta()
)