package com.example.recipebook
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable



@Composable
fun BottomNavGraph(navController: NavHostController , modifier : Modifier) {
    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Home.route
    ) {
        composable(route = BottomBarScreen.Home.route) {
            HomeScreen()
        }
        composable(route = BottomBarScreen.Profile.route) {
            RecipesScreen()
        }
        composable(route = BottomBarScreen.Settings.route) {
            CategoryScreen()
        }
    }
}