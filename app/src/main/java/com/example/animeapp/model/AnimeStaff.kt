package com.example.animeapp.model


import com.google.gson.annotations.SerializedName

data class AnimeStaff(
    @SerializedName("links")
    val links: Links? = Links()
)