package com.example.recipebook.navigation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.recipebook.BottomBarScreen
import com.example.recipebook.HomeScreen
import com.example.recipebook.ProfileScreen
import com.example.recipebook.SettingScreen


@Composable
fun BottomNavGraph(navController: NavHostController ) {
    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Home.route
    ) {
        composable(route = BottomBarScreen.Home.route) {
            HomeScreen()
        }
        composable(route = BottomBarScreen.Profile.route) {
            ProfileScreen()
        }
        composable(route = BottomBarScreen.Settings.route) {
            SettingScreen()
        }
    }
}