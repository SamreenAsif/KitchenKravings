package com.example.recipebook.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.recipebook.MainScreen
import com.example.recipebook.presentation.login_screen.SignInScreen
import com.example.recipebook.presentation.signup_screen.SignUpScreen
import dagger.hilt.android.lifecycle.HiltViewModel

@Composable
fun AuthNavGraph(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = Screens.SignUpScreen.route
    ) {

        composable(route = Screens.SignInScreen.route) {
            SignInScreen(
                navController = navController,
            )
        }
        composable(route = Screens.SignUpScreen.route) {
            SignUpScreen(navController = navController)
        }
        composable(route = Screens.MainScreen.route) {
            MainScreen()
        }
    }
}

