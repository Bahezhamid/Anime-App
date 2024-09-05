package com.example.animeapp.model


import com.google.gson.annotations.SerializedName

data class Titles(
    @SerializedName("en")
    val en: String? = null,
    @SerializedName("en_jp")
    val enJp: String? = null,
    @SerializedName("en_us")
    val enUs: String? = null,
    @SerializedName("ja_jp")
    val jaJp: String? = null
)