package com.example.recipebook.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.recipebook.MainScreen
import com.example.recipebook.presentation.ForgotPasswordScreen
import com.example.recipebook.presentation.GoogleSignInManager
import com.example.recipebook.presentation.login_screen.SignInScreen
import com.example.recipebook.presentation.signup_screen.SignUpScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AuthNavGraph(
    googleSignInManager: GoogleSignInManager?,
    navController: NavHostController = rememberNavController(),

) {
    NavHost(
        navController = navController,
        startDestination = Screens.SignInScreen.route
    ) {

        composable(route = Screens.SignInScreen.route) {
            SignInScreen(
                navController = navController,
                googleSignInManager
            )
        }
        composable(route = Screens.SignUpScreen.route) {
            SignUpScreen(navController = navController, googleSignInManager)
        }
        composable(route = Screens.MainScreen.route) {
            MainScreen()
        }

        composable(route = Screens.ForgotPasswordScreen.route) {
            ForgotPasswordScreen(navController = navController)
        }

    }
}

