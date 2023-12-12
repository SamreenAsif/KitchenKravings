package com.example.recipebook.navigation

sealed class Screens(val route: String) {
    object SignInScreen : Screens(route = "SignIn_Screen")
    object SignUpScreen : Screens(route = "SignUp_Screen")
    object MainScreen : Screens(route = "Main_Screen")
    object WelcomeScreen : Screens(route = "Welcome_Screen")

}