package com.example.animeapp

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.VideoLibrary
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.animeapp.ui.AppViewModelProvider
import com.example.animeapp.ui.screens.ProfilePage
import com.example.animeapp.ui.navigation.AnimeScreen
import com.example.animeapp.ui.screens.AllAnimeScreen.AllAnimePage
import com.example.animeapp.ui.screens.AllAnimeScreen.AllAnimeViewScreenModel
import com.example.animeapp.ui.screens.AnimeChapterScreen.AnimeChaptersScreen
import com.example.animeapp.ui.screens.AnimeDetailsPage.AnimeDetailsPage
import com.example.animeapp.ui.screens.CharactersDetailsPage.CharactersDetailsPage
import com.example.animeapp.ui.screens.EmailAndPasswordChangePage.EmailAndPasswordChangeScreen
import com.example.animeapp.ui.screens.EmailAndPasswordChangePage.EmailAndPasswordChangeViewModel
import com.example.animeapp.ui.screens.HomePage.HomePageViewModel
import com.example.animeapp.ui.screens.HomePage.HomeScreen
import com.example.animeapp.ui.screens.LandingPage.LandingPage
import com.example.animeapp.ui.screens.SavedAnimeScreen.SavedAnimePage
import com.example.animeapp.ui.screens.SavedAnimeScreen.SavedAnimeViewModel
import com.example.animeapp.ui.screens.SearchScreen.SearchScreen
import com.example.animeapp.ui.screens.SearchScreen.SearchScreenViewModel
import com.example.animeapp.ui.screens.UserDetailsScreen.UserDetailsViewModel
import com.example.animeapp.ui.screens.UserDetailsScreen.UserDetainsScreen
import com.example.animeapp.ui.screens.logInAndSignUp.LoginAndSignUpPage
import com.example.animeapp.ui.screens.logInAndSignUp.LoginAndSignUpViewModel

@Composable
fun AnimeApp(
    navController: NavHostController = rememberNavController(),
    loginAndSignUpViewModel: LoginAndSignUpViewModel = viewModel(factory = AppViewModelProvider.Factory),
    homePageViewModel: HomePageViewModel = viewModel(factory = AppViewModelProvider.Factory),
    savedAnimeViewModel: SavedAnimeViewModel = viewModel(factory = AppViewModelProvider.Factory),
    allAnimeViewScreenModel: AllAnimeViewScreenModel = viewModel(factory = AppViewModelProvider.Factory),
    emailAndPasswordChangeViewModel: EmailAndPasswordChangeViewModel = viewModel(),
    userDetailsViewModel: UserDetailsViewModel = viewModel(),
    searchScreenViewModel: SearchScreenViewModel = viewModel(factory = AppViewModelProvider.Factory)
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
                loginAndSignUpViewModel = loginAndSignUpViewModel,
                homePageViewModel = homePageViewModel,
                onLoginAndSignUpButtonClicked = {navController.navigate(AnimeScreen.HomePage.route)},
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
                homePageViewModel = homePageViewModel,
                onForgetPasswordClicked = {navController.navigate(AnimeScreen.ForgetPasswordPage.route)},
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
                homePageViewModel = homePageViewModel,
                viewModel = loginAndSignUpViewModel
            )
        }
        composable(route = AnimeScreen.ForgetPasswordPage.route) {
            LoginAndSignUpPage(
                title = "Forget Password?",
                description = "Enter your email to receive a password reset link.",
                isSignUpPage = false ,
                onButtonText = "Confirm",
                authSwitchMessage = stringResource(id = R.string.don_t_have_an_account_signup_here),
                onBackPressed = { navController.navigateUp() },
                onAuthSwitchClick = { navController.navigate(AnimeScreen.SignUp.route) },
                onLoginAndSignUpButtonClicked = { /*TODO*/ },
                viewModel = loginAndSignUpViewModel,
                isForgetPasswordPage = true,
                onForgetPasswordButtonClicked = {navController.navigate(AnimeScreen.LogIn.route)},
                homePageViewModel = homePageViewModel
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
                },
                homePageViewModel = homePageViewModel,
                onSearchButtonClicked = {
                    navController.navigate("searchScreen")
                }

            )
        }
        composable(
            route = AnimeScreen.AnimeDetailsPage.route
        ) { backStackEntry ->
            val animeId = backStackEntry.arguments?.getString("animeId")?.toIntOrNull()
            val context = LocalContext.current
            AnimeDetailsPage(
                animeId = animeId,
                onBackPressed = { navController.navigateUp() },
                onPlayButtonClicked = {id ->
                    navController.navigate("animeChaptersScreen/$id")
                },
                onCharacterClicked = { characterId ->
                    navController.navigate("characterDetailsPage/$characterId")
                },
                onShareButtonClicked = { subject: String, animeDetails: String  , link : String ->
                    shareAnime(context, subject = subject, animeDetails = animeDetails , link = link)
                },
                homePageViewModel = homePageViewModel
            )
        }
        composable(route = AnimeScreen.SavedAnimeScreen.route){
            SavedAnimePage(
                onHomeClicked = { navController.navigate(AnimeScreen.HomePage.route) },
                onBookClicked = {navController.navigate(AnimeScreen.AllAnimeScreen.route)},
                onProfileClicked = {navController.navigate(AnimeScreen.ProfilePage.route)},
                loginAndSignUpViewModel = loginAndSignUpViewModel,
                savedAnimeViewModel = savedAnimeViewModel,
                onAnimeClicked = { animeId ->
                    navController.navigate("animeDetails/$animeId")
                },
                onCreateNewListClicked = {navController.navigate(AnimeScreen.AllAnimeScreen.route)},
                onSearchButtonClicked = {navController.navigate("searchScreen")}
            )
        }
        composable(route = AnimeScreen.AllAnimeScreen.route) {
            AllAnimePage(
                onHomeButtonClicked = {navController.navigate(AnimeScreen.HomePage.route)},
                onSavedButtonClicked = {navController.navigate(AnimeScreen.SavedAnimeScreen.route)},
                onProfileClicked = {navController.navigate(AnimeScreen.ProfilePage.route)},
                onAnimeClicked = {animeId->
                    navController.navigate("animeDetails/$animeId")
                },
                allAnimeViewScreenModel = allAnimeViewScreenModel,
                onSearchButtonClicked = {navController.navigate("searchScreen")}
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
                onUserDateClicked = {navController.navigate(AnimeScreen.UserDetailsPage.route)},
                onEmailChangeClicked = {navController.navigate(AnimeScreen.EmailChangePage.route)},
                onPasswordChangeClicked = {navController.navigate(AnimeScreen.PasswordChangePage.route)},
                onSignOutButtonClicked = {
                    loginAndSignUpViewModel.signOut()
                    navController.navigate(AnimeScreen.Start
                        .route) {
                        popUpTo(navController.graph.startDestinationId) {
                            inclusive = true
                        }
                    }
                    savedAnimeViewModel.removeSavedAnime()
                },

                userData = loginAndSignUpViewModel.loginUiState.collectAsState().value
            )
        }
        composable(route = AnimeScreen.UserDetailsPage.route) {
            UserDetainsScreen(
                onBackButtonClicked = {navController.navigateUp()},
                userData = loginAndSignUpViewModel.loginUiState.collectAsState().value,
                userDetailsViewModel = userDetailsViewModel
            )
        }
        composable(route = AnimeScreen.EmailChangePage.route) {
            EmailAndPasswordChangeScreen(
                onBackButtonClicked = { navController.navigateUp()},
                isPasswordChangePage = false,
                emailAndPasswordChangeViewModel = emailAndPasswordChangeViewModel,
                onSignOutClicked = {
                    loginAndSignUpViewModel.signOut()
                    navController.navigate(AnimeScreen.Start
                        .route) {
                        popUpTo(navController.graph.startDestinationId) {
                            inclusive = true
                        }
                    }
                    savedAnimeViewModel.removeSavedAnime()
                },
            )
        }
        composable(route = AnimeScreen.PasswordChangePage.route) {
            EmailAndPasswordChangeScreen(
                onBackButtonClicked = { navController.navigateUp() },
                isPasswordChangePage = true ,
                emailAndPasswordChangeViewModel = emailAndPasswordChangeViewModel,
                onSignOutClicked = {
                    loginAndSignUpViewModel.signOut()
                    navController.navigate(AnimeScreen.Start
                        .route) {
                        popUpTo(navController.graph.startDestinationId) {
                            inclusive = true
                        }
                    }
                    savedAnimeViewModel.removeSavedAnime()
                },
            )
        }
        composable(route = AnimeScreen.SearchScreen.route){
            SearchScreen (
                onBackPressed = {navController.navigateUp()},
                onAnimeClicked = {animeId->
                    navController.navigate("animeDetails/$animeId")
                },
                searchScreenViewModel = searchScreenViewModel
            )
        }
    }
}

@Composable
fun AnimeBottomNavigationBar(
    selectedTab: BottomNavItem,
    onTabSelected: (BottomNavItem) -> Unit,
    onHomeClick: () -> Unit,
    onSavedClick: () -> Unit,
    onBookClick: () -> Unit,
    onProfileClick: () -> Unit
) {
        BottomNavigation(
            backgroundColor = MaterialTheme.colorScheme.secondary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            elevation = 8.dp,

            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .navigationBarsPadding()
        ) {
            BottomNavigationItem(
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_home),
                        contentDescription = "Home",
                        modifier = Modifier
                            .size(25.dp)
                            .graphicsLayer(alpha = if (selectedTab == BottomNavItem.Home) 1f else 0.5f),
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                },
                selected = selectedTab == BottomNavItem.Home,
                onClick = {
                    onTabSelected(BottomNavItem.Home)
                    onHomeClick()
                }
            )
            BottomNavigationItem(
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_saved),
                        contentDescription = "Saved",
                        modifier = Modifier
                            .size(25.dp)
                            .graphicsLayer(alpha = if (selectedTab == BottomNavItem.Saved) 1f else 0.5f),
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                },
                selected = selectedTab == BottomNavItem.Saved,
                onClick = {
                    onTabSelected(BottomNavItem.Saved)
                    onSavedClick()
                }
            )
            BottomNavigationItem(
                icon = {
                    Icon(
                        imageVector = Icons.Filled.VideoLibrary,
                        contentDescription = "Anime And Manga",
                        modifier = Modifier
                            .size(25.dp)
                            .graphicsLayer(alpha = if (selectedTab == BottomNavItem.Book) 1f else 0.5f),
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                },
                selected = selectedTab == BottomNavItem.Book,
                onClick = {
                    onTabSelected(BottomNavItem.Book)
                    onBookClick()
                }
            )
            BottomNavigationItem(
                icon = {
                    Icon(
                        imageVector = Icons.Filled.Person,
                        contentDescription = "Profile",
                        modifier = Modifier
                            .size(25.dp)
                            .graphicsLayer(alpha = if (selectedTab == BottomNavItem.Profile) 1f else 0.5f),
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                },
                selected = selectedTab == BottomNavItem.Profile,
                onClick = {
                    onTabSelected(BottomNavItem.Profile)
                    onProfileClick()
                }
            )
        }
}

enum class BottomNavItem {
    Home, Saved, Book, Profile
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun AnimeTopAppBar(
    title: String,
    isBackButton: Boolean = false,
    onBackButtonClicked : () -> Unit ={},
    onSearchButtonClicked : () -> Unit = {},
    backGroundColor : Color = MaterialTheme.colorScheme.secondary,
) {
        TopAppBar(
            title = {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(start = 10.dp)
                )
            },
            navigationIcon = {
                if (isBackButton) {
                    IconButton(onClick = onBackButtonClicked) {
                        Icon(
                            imageVector = Icons.Outlined.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier.padding(start = 10.dp)
                        )
                    }
                }
            },
            actions = {
                if (!isBackButton) {
                    Icon(
                        imageVector = Icons.Outlined.Search,
                        contentDescription = "Search",
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier
                            .padding(end = 10.dp)
                            .size(25.dp)
                            .clickable { onSearchButtonClicked() }
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor =backGroundColor
            ),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()

        )
}
private fun shareAnime(context: Context, subject: String, animeDetails: String, link: String) {
    val shareText = "$animeDetails\n\nCheck this out: $link"

    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, subject)
        putExtra(Intent.EXTRA_TEXT, shareText)
    }
    context.startActivity(
        Intent.createChooser(
            intent,
            context.getString(R.string.sharing_anime)
        )
    )
}


