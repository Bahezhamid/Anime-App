package com.example.animeapp.model


import com.google.gson.annotations.SerializedName

data class Links(
    @SerializedName("self")
    val self: String? = null,
    @SerializedName("related")
    val related: String? = null,
)