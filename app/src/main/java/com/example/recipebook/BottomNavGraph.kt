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
            HomeScreen(navController)
        }
        composable(route = BottomBarScreen.Recipes.route) {
            RecipesScreen(navController = navController)
        }
        composable(route = BottomBarScreen.Categories.route) {
            CategoryScreen()
        }
        composable(route = "videoPage") {
            VideoPage(navController = navController)
        }
    }
}