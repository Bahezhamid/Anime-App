package com.example.animeapp

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.VideoLibrary
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
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
import com.example.animeapp.ui.navigation.AnimeScreen
import com.example.animeapp.ui.screens.HomePage.HomeScreen
import com.example.animeapp.ui.screens.LandingPage
import com.example.animeapp.ui.screens.logInAndSignUp.LoginAndSignUpPage
import com.example.animeapp.ui.screens.logInAndSignUp.LoginAndSignUpViewModel


@Composable
fun AnimeApp(
    navController: NavHostController = rememberNavController(),
    loginAndSignUpViewModel: LoginAndSignUpViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = AnimeScreen.valueOf(
        backStackEntry?.destination?.route ?: AnimeScreen.Start.name
    )
  NavHost( navController = navController,
      startDestination = AnimeScreen.Start.name,
      enterTransition = { EnterTransition.None },
      exitTransition = { ExitTransition.None },
      popEnterTransition = { EnterTransition.None },
      popExitTransition = { ExitTransition.None },
      modifier = Modifier
          .fillMaxSize()
  ) {
      composable(route = AnimeScreen.Start.name){
          LandingPage(
              onLoginButtonClicked = {navController.navigate(AnimeScreen.LogIn.name)},
              onSignButtonClicked = {navController.navigate(AnimeScreen.SignUp.name)},
              modifier = Modifier.fillMaxSize()
          )
      }
      composable(route = AnimeScreen.LogIn.name){
          LoginAndSignUpPage(
              title = stringResource(R.string.welcome_back),
              description = stringResource(R.string.login_to_your_account),
              isSignUpPage = false,
              onButtonText = stringResource(R.string.login),
              authSwitchMessage = stringResource(R.string.don_t_have_an_account_signup_here),
              onBackPressed = {navController.navigateUp()},
              onAuthSwitchClick = {navController.navigate(AnimeScreen.SignUp.name)},
              onLoginAndSignUpButtonClicked = {navController.navigate(AnimeScreen.HomePage.name)},
              viewModel =loginAndSignUpViewModel
          )
      }
      composable(route = AnimeScreen.SignUp.name) {
          LoginAndSignUpPage(
              title = stringResource(id = R.string.signup),
              description = stringResource(R.string.create_an_account),
              isSignUpPage = true,
              onButtonText = stringResource(id = R.string.signup),
              authSwitchMessage = stringResource(R.string.already_have_an_account_login_here),
              onBackPressed = {navController.navigateUp()},
              onAuthSwitchClick = {navController.navigate(AnimeScreen.LogIn.name)},
              onLoginAndSignUpButtonClicked = {navController.navigate(AnimeScreen.HomePage.name)},
              viewModel = loginAndSignUpViewModel
          )
      }
      composable(route = AnimeScreen.HomePage.name) {
          HomeScreen(
              loginAndSignUpViewModel = loginAndSignUpViewModel
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
        modifier = Modifier.fillMaxWidth(),
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

