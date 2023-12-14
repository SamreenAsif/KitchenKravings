package com.example.recipebook.navigation

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.recipebook.MainScreen
import com.example.recipebook.WelcomeScreen
import com.example.recipebook.presentation.ForgotPasswordScreen
import com.example.recipebook.presentation.GoogleSignInManager
import com.example.recipebook.presentation.login_screen.SignInScreen
import com.example.recipebook.presentation.signup_screen.SignUpScreen

var startDest: String = ""

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AuthNavGraph(
    googleSignInManager: GoogleSignInManager?,
    signInResultLiveData: MutableLiveData<Boolean>,
    navController: NavHostController = rememberNavController(),
) {
    var welcomeScreenVisible by remember { mutableStateOf(true) }

    if (welcomeScreenVisible) {
        startDest = Screens.WelcomeScreen.route
    } else {
        if(googleSignInManager!!.isUserAlreadySignIn){
            navController.navigate(Screens.MainScreen.route)
        }else{
            navController.navigate(Screens.SignInScreen.route)
        }
    }
    NavHost(
        navController = navController,
        startDestination = startDest
    ) {

        composable(route = Screens.SignInScreen.route) {
            SignInScreen(
                navController = navController,
                googleSignInManager,
                signInResultLiveData
            )
        }
        composable(route = Screens.SignUpScreen.route) {
            SignUpScreen(navController = navController, googleSignInManager)
        }
        composable(route = Screens.MainScreen.route) {
            MainScreen(googleSignInManager, myNavController = navController)
        }

        composable(route = Screens.ForgotPasswordScreen.route) {
            ForgotPasswordScreen(navController = navController)
        }
        composable(route = Screens.WelcomeScreen.route) {
            WelcomeScreen {
                welcomeScreenVisible = false
                Log.d("TAG", "Navigating from WelcomeScreen to SignInScreen")
                if(googleSignInManager!!.isUserAlreadySignIn){
                    navController.navigate(Screens.MainScreen.route)
                }else{
                    navController.navigate(Screens.SignInScreen.route)
                }

            }
        }

    }
}
