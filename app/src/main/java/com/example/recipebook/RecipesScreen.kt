package com.example.recipebook

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import com.example.recipebook.firebaselogic.FetchRecipesFromFirebase

@Composable
fun RecipesScreen(navController: NavController) {
    // State to hold the list of recipes
    var recipes by remember { mutableStateOf(emptyList<Recipe>()) }

    // Fetch recipes from Firebase and update the recipes state
    recipes = FetchRecipesFromFirebase()

    // Display the recipes
    RecipePage(recipes = recipes, navController)
}


