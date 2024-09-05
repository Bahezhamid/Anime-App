package com.example.animeapp.model


import com.google.gson.annotations.SerializedName

data class Meta(
    @SerializedName("dimensions")
    val dimensions: Dimensions? = null,
    @SerializedName("count")
    val count: Int? = null
)