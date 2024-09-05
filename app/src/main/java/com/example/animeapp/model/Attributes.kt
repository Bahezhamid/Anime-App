package com.example.animeapp.model


import com.google.gson.annotations.SerializedName

data class Attributes(
    @SerializedName("abbreviatedTitles")
    val abbreviatedTitles: List<String>? = listOf(),
    @SerializedName("ageRating")
    val ageRating: String? = "",
    @SerializedName("ageRatingGuide")
    val ageRatingGuide: String? = "",
    @SerializedName("averageRating")
    val averageRating: String? = "",
    @SerializedName("canonicalTitle")
    val canonicalTitle: String? = "",
    @SerializedName("coverImage")
    val coverImage: CoverImage? = CoverImage(),
    @SerializedName("coverImageTopOffset")
    val coverImageTopOffset: Int? = 0,
    @SerializedName("createdAt")
    val createdAt: String? = "",
    @SerializedName("description")
    val description: String? = "",
    @SerializedName("endDate")
    val endDate: String? = "",
    @SerializedName("episodeCount")
    val episodeCount: Int? = 0,
    @SerializedName("episodeLength")
    val episodeLength: Int? = 0,
    @SerializedName("favoritesCount")
    val favoritesCount: Int? = 0,
    @SerializedName("nextRelease")
    val nextRelease: Any? = Any(),
    @SerializedName("nsfw")
    val nsfw: Boolean? = false,
    @SerializedName("popularityRank")
    val popularityRank: Int? = 0,
    @SerializedName("posterImage")
    val posterImage: PosterImage? = PosterImage(),
    @SerializedName("ratingFrequencies")
    val ratingFrequencies: RatingFrequencies? = RatingFrequencies(),
    @SerializedName("ratingRank")
    val ratingRank: Int? = 0,
    @SerializedName("showType")
    val showType: String? = "",
    @SerializedName("slug")
    val slug: String? = "",
    @SerializedName("startDate")
    val startDate: String? = "",
    @SerializedName("status")
    val status: String? = "",
    @SerializedName("subtype")
    val subtype: String? = "",
    @SerializedName("synopsis")
    val synopsis: String? = "",
    @SerializedName("tba")
    val tba: String? = "",
    @SerializedName("titles")
    val titles: Titles? = Titles(),
    @SerializedName("totalLength")
    val totalLength: Int? = 0,
    @SerializedName("updatedAt")
    val updatedAt: String? = "",
    @SerializedName("userCount")
    val userCount: Int? = 0,
    @SerializedName("youtubeVideoId")
    val youtubeVideoId: String? = ""
)