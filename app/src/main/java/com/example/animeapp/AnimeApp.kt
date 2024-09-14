package com.example.animeapp

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.VideoLibrary
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.animeapp.ui.AppViewModelProvider
import com.example.animeapp.ui.ProfilePage
import com.example.animeapp.ui.navigation.AnimeScreen
import com.example.animeapp.ui.screens.AllAnimeScreen.AllAnimePage
import com.example.animeapp.ui.screens.AllAnimeScreen.AllAnimeScreen
import com.example.animeapp.ui.screens.AnimeChapterScreen.AnimeChaptersScreen
import com.example.animeapp.ui.screens.AnimeDetailsPage.AnimeDetailsPage
import com.example.animeapp.ui.screens.CharactersDetailsPage.CharactersDetailsPage
import com.example.animeapp.ui.screens.HomePage.HomePageViewModel
import com.example.animeapp.ui.screens.HomePage.HomeScreen
import com.example.animeapp.ui.screens.LandingPage
import com.example.animeapp.ui.screens.SavedAnimePage
import com.example.animeapp.ui.screens.logInAndSignUp.LoginAndSignUpPage
import com.example.animeapp.ui.screens.logInAndSignUp.LoginAndSignUpViewModel


@Composable
fun AnimeApp(
    navController: NavHostController = rememberNavController(),
    loginAndSignUpViewModel: LoginAndSignUpViewModel = viewModel(factory = AppViewModelProvider.Factory),
    homePageViewModel: HomePageViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    NavHost(
        navController = navController,
        startDestination = AnimeScreen.Start.route,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None },
        modifier = Modifier.fillMaxSize()
    ) {
        composable(route = AnimeScreen.Start.route) {
            LandingPage(
                onLoginButtonClicked = { navController.navigate(AnimeScreen.LogIn.route) },
                onSignButtonClicked = { navController.navigate(AnimeScreen.SignUp.route) },
                modifier = Modifier.fillMaxSize()
            )
        }
        composable(route = AnimeScreen.LogIn.route) {
            LoginAndSignUpPage(
                title = stringResource(R.string.welcome_back),
                description = stringResource(R.string.login_to_your_account),
                isSignUpPage = false,
                onButtonText = stringResource(R.string.login),
                authSwitchMessage = stringResource(R.string.don_t_have_an_account_signup_here),
                onBackPressed = { navController.navigateUp() },
                onAuthSwitchClick = { navController.navigate(AnimeScreen.SignUp.route) },
                onLoginAndSignUpButtonClicked = { navController.navigate(AnimeScreen.HomePage.route) },
                viewModel = loginAndSignUpViewModel
            )
        }
        composable(route = AnimeScreen.SignUp.route) {
            LoginAndSignUpPage(
                title = stringResource(id = R.string.signup),
                description = stringResource(R.string.create_an_account),
                isSignUpPage = true,
                onButtonText = stringResource(id = R.string.signup),
                authSwitchMessage = stringResource(R.string.already_have_an_account_login_here),
                onBackPressed = { navController.navigateUp() },
                onAuthSwitchClick = { navController.navigate(AnimeScreen.LogIn.route) },
                onLoginAndSignUpButtonClicked = { navController.navigate(AnimeScreen.HomePage.route) },
                viewModel = loginAndSignUpViewModel
            )
        }
        composable(route = AnimeScreen.HomePage.route) {
            HomeScreen(
                onAnimeClicked = { animeId ->
                navController.navigate("animeDetails/$animeId")
            },
                onPlayButtonClicked = {animeId ->
                    navController.navigate("animeChaptersScreen/$animeId")
                },
                onSavedClicked = {navController.navigate(AnimeScreen.SavedAnimeScreen.route)},
                onBookClicked = {navController.navigate(AnimeScreen.AllAnimeScreen.route)},
                onProfileClicked = {navController.navigate(AnimeScreen.ProfilePage.route)},
                onInfoButtonClicked = {animeId ->
                    navController.navigate("animeDetails/$animeId")
                }
            )
        }
        composable(
            route = AnimeScreen.AnimeDetailsPage.route
        ) { backStackEntry ->
            val animeId = backStackEntry.arguments?.getString("animeId")?.toIntOrNull()
            AnimeDetailsPage(
                animeId = animeId,
                onBackPressed = { navController.navigateUp() },
                onPlayButtonClicked = {id ->
                    navController.navigate("animeChaptersScreen/$id")
                },
                onCharacterClicked = { characterId ->
                    navController.navigate("characterDetailsPage/$characterId")
                }
            )
        }
        composable(route = AnimeScreen.SavedAnimeScreen.route){
            SavedAnimePage(
                onHomeClicked = { navController.navigate(AnimeScreen.HomePage.route) },
                onBookClicked = {navController.navigate(AnimeScreen.AllAnimeScreen.route)},
                onProfileClicked = {navController.navigate(AnimeScreen.ProfilePage.route)}
            )
        }
        composable(route = AnimeScreen.AllAnimeScreen.route) {
            AllAnimePage(
                onHomeButtonClicked = {navController.navigate(AnimeScreen.HomePage.route)},
                onSavedButton = {navController.navigate(AnimeScreen.SavedAnimeScreen.route)},
                onProfileClicked = {navController.navigate(AnimeScreen.ProfilePage.route)},
                onAnimeClicked = {animeId->
                    navController.navigate("animeDetails/$animeId")
                }
            )
        }
        composable(route = AnimeScreen.CharacterDetailsPage.route) {  backStackEntry ->
          val characterId = backStackEntry.arguments?.getString("characterId")?.toIntOrNull()
            if(characterId != null){
                CharactersDetailsPage(
                    characterId = characterId,
                    onAnimeClicked = {animeId ->
                        navController.navigate("animeDetails/$animeId")
                    },
                    onBackButtonClicked = {navController.navigateUp()}
                    )
            }
        }
        composable(route = AnimeScreen.AnimeChaptersScreen.route) {
                backStackEntry ->
            val animeId = backStackEntry.arguments?.getString("animeId")?.toIntOrNull()
            if (animeId != null) {
                AnimeChaptersScreen(
                    animeId = animeId,
                    onBackButtonClicked = { navController.navigateUp() }
                )
            }
        }
        composable(route = AnimeScreen.ProfilePage.route) {
            ProfilePage(
                onHomeClicked = {navController.navigate(AnimeScreen.HomePage.route)},
                onSavedClicked =  {navController.navigate(AnimeScreen.SavedAnimeScreen.route)},
                onBookClicked =  {navController.navigate(AnimeScreen.AllAnimeScreen.route)},
            )
        }

    }
}


@Composable

fun AnimeBottomAppBar(
    onHomeClick: () -> Unit,
    onSavedClick: () -> Unit,
    onBookClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    BottomAppBar(
        tonalElevation = 8.dp,
        contentPadding = PaddingValues(horizontal = 16.dp),
        containerColor = MaterialTheme.colorScheme.primary,
        modifier = Modifier.fillMaxWidth().wrapContentHeight(),
        contentColor = MaterialTheme.colorScheme.onPrimary
    ) {
        Spacer(modifier = Modifier.weight(0.5f))
        IconButton(onClick = onHomeClick) {
            Icon(
                painter = painterResource(id = R.drawable.ic_home),
                contentDescription = "Home",
                modifier = Modifier.size(60.dp)
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        IconButton(onClick = onSavedClick) {
            Icon(
                painter = painterResource(id = R.drawable.ic_saved),
                contentDescription = "Saved",
                modifier = Modifier.size(60.dp)
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        IconButton(onClick = onBookClick) {
            Icon(imageVector = Icons.Filled.VideoLibrary,
                contentDescription = "Anime And Manga",
                modifier = Modifier.size(60.dp)
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        IconButton(onClick = onProfileClick) {
            Icon(imageVector = Icons.Filled.Person,
                contentDescription = "Profile",
                modifier = Modifier.size(60.dp)
            )
        }
        Spacer(modifier = Modifier.weight(0.5f))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun AnimeTopAppBar(
    title: String,
    isBackButton: Boolean = false,
    onBackButtonClicked : () -> Unit ={},
    backGroundColor : Color = MaterialTheme.colorScheme.primary,
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall
            )
        },
        navigationIcon = {
            if (isBackButton) {
                IconButton(onClick = onBackButtonClicked) {
                    Icon(
                        imageVector = Icons.Outlined.ArrowBack,
                        contentDescription = "Back",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        },
        actions = {
            if(!isBackButton){
            Icon(
                imageVector = Icons.Outlined.Search,
                contentDescription = "Search",
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.size(50.dp)
            )
                }
        },
        modifier = Modifier.fillMaxWidth()
            .wrapContentHeight(),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = backGroundColor
        )
    )
}

