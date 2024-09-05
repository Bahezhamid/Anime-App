package com.example.animeapp.model


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("attributes")
    val attributes: Attributes? = Attributes(),
    @SerializedName("id")
    val id: String? = "",
    @SerializedName("links")
    val links: Links? = Links(),
    @SerializedName("relationships")
    val relationships: Relationships? = Relationships(),
    @SerializedName("type")
    val type: String? = ""
)