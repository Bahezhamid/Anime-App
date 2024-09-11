package com.example.animeapp.model


import com.google.gson.annotations.SerializedName

data class Character(
    @SerializedName("images")
    val images: Images? = Images(),
    @SerializedName("mal_id")
    val malId: Int? = 0,
    @SerializedName("name")
    val name: String? = "",
    @SerializedName("url")
    val url: String? = ""
)