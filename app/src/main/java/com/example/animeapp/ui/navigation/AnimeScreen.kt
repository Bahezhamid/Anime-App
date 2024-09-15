package com.example.animeapp.ui.navigation

enum class AnimeScreen(val route: String) {
    Start("start"),
    LogIn("login"),
    SignUp("signup"),
    HomePage("homePage"),
    AnimeDetailsPage("animeDetails/{animeId}"),
    SavedAnimeScreen("savedAnimeScreen"),
    AllAnimeScreen("allAnimeScreen"),
    AnimeChaptersScreen("animeChaptersScreen/{animeId}"),
    ProfilePage("profilePage"),
    CharacterDetailsPage("characterDetailsPage/{characterId}"),
    UserDetailsPage("userDetailsPage")
}