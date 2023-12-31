package com.example.recipebook

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.recipebook.data.Recipe
import com.example.recipebook.firebaselogic.fetchRecipesFromFirebase

@Composable
fun RecipesScreen(navController: NavController) {
    // State to hold the list of recipes
    var recipes by remember { mutableStateOf(emptyList<Recipe>()) }
    DisposableEffect(Unit) {
        // Fetch recipes from Firebase and update the recipes state
        fetchRecipesFromFirebase(null) { fetchedRecipes ->
            recipes = fetchedRecipes
            Log.d("Recipes in screen", "" + recipes.size)
        }

        onDispose {
            // You can add cleanup logic here if needed
        }
    }
    Log.d("Recipes in screen" , "" +recipes.size)

    val paddingValue = 80.dp
    // Display the recipes
    RecipePage(recipes = recipes,navController = navController,bottompadding = paddingValue)
}


