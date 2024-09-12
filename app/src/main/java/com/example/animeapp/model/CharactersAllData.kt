package com.example.animeapp.model


import com.google.gson.annotations.SerializedName

data class CharactersAllData(
    @SerializedName("data")
    val `data`: Data? = Data()
)