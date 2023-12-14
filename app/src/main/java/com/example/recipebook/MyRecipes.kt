package com.example.recipebook

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.recipebook.data.Recipe
import com.example.recipebook.firebaselogic.searchRecipesById

@Composable
fun MyRecipes(navController:NavController){
    var recipes by remember { mutableStateOf(emptyList<Recipe>()) }
    LaunchedEffect(Unit) {
        // You need to provide appropriate values for searchTerm and searchType
        recipes = searchRecipesById()

        // Optional cleanup logic here if needed
    }
    Log.d("Recipes in screen" , "" +recipes.size)

    val paddingValue = 80.dp
    // Display the recipes
    RecipePage(recipes = recipes,navController = navController,bottompadding = paddingValue)
}