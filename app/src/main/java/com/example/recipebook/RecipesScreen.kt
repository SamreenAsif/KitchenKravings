package com.example.recipebook

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.recipebook.data.Recipe
import com.example.recipebook.firebaselogic.FetchRecipesFromFirebase

@Composable
fun RecipesScreen(navController: NavController) {
    // State to hold the list of recipes
    var recipes by remember { mutableStateOf(emptyList<Recipe>()) }

    // Fetch recipes from Firebase and update the recipes state
    recipes = FetchRecipesFromFirebase(null)
    Log.d("Recipes in screen" , "" +recipes.size)

    val paddingValue = 80.dp
    // Display the recipes
    RecipePage(recipes = recipes,navController = navController,bottompadding = paddingValue)
}


