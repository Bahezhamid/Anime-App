package com.example.animeapp

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.animeapp.ui.AppViewModelProvider
import com.example.animeapp.ui.navigation.AnimeScreen
import com.example.animeapp.ui.screens.LandingPage
import com.example.animeapp.ui.screens.logInAndSignUp.LoginAndSignUpPage



@Composable
fun AnimeApp(
    navController: NavHostController = rememberNavController()
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
              viewModel = viewModel(factory = AppViewModelProvider.Factory)
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
              viewModel = viewModel(factory = AppViewModelProvider.Factory)
          )
      }
  }
}

