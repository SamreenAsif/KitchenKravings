package com.example.recipebook
import android.util.Log
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

        composable(route = "videoPage/{recipeId}") { backStackEntry ->
            val recipeId = backStackEntry.arguments?.getString("recipeId")
            if (recipeId != null) {
                Log.d("RecipeId" , recipeId)
            }
            else
                Log.d("RecipeId" , "recipeid is null")

            RecipeDetailsScreen(navController = navController, recipeId = recipeId)
        }
            composable(route = "addRecipe") {
            AddRecipeScreen(onRecipeAdded ={ /* Handle recipe added */ })
        }
//        composable(route = "getRecipe") {
//            VideoTest()
//        }
    }
}