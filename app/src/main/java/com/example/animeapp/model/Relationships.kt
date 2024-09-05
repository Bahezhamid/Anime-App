package com.example.animeapp.model


import com.google.gson.annotations.SerializedName

data class Relationships(
    @SerializedName("animeCharacters")
    val animeCharacters: AnimeCharacters? = AnimeCharacters(),
    @SerializedName("animeProductions")
    val animeProductions: AnimeProductions? = AnimeProductions(),
    @SerializedName("animeStaff")
    val animeStaff: AnimeStaff? = AnimeStaff(),
    @SerializedName("castings")
    val castings: Castings? = Castings(),
    @SerializedName("categories")
    val categories: Categories? = Categories(),
    @SerializedName("characters")
    val characters: Characters? = Characters(),
    @SerializedName("episodes")
    val episodes: Episodes? = Episodes(),
    @SerializedName("genres")
    val genres: Genres? = Genres(),
    @SerializedName("installments")
    val installments: Installments? = Installments(),
    @SerializedName("mappings")
    val mappings: Mappings? = Mappings(),
    @SerializedName("mediaRelationships")
    val mediaRelationships: MediaRelationships? = MediaRelationships(),
    @SerializedName("productions")
    val productions: Productions? = Productions(),
    @SerializedName("quotes")
    val quotes: Quotes? = Quotes(),
    @SerializedName("reviews")
    val reviews: Reviews? = Reviews(),
    @SerializedName("staff")
    val staff: Staff? = Staff(),
    @SerializedName("streamingLinks")
    val streamingLinks: StreamingLinks? = StreamingLinks()
)