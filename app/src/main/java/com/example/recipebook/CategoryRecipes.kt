package com.example.recipebook

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.recipebook.data.Recipe
import com.example.recipebook.firebaselogic.SearchType
import com.example.recipebook.firebaselogic.searchRecipesFromFirebase

@Composable
fun CategoryRecipes(navController: NavController,searchTerm : String) {
    // State to hold the list of recipes
    var recipes by remember { mutableStateOf(emptyList<Recipe>()) }
    Log.d("SearchTerm ", searchTerm)
    // Fetch recipes from Firebase and update the recipes state
    recipes = searchRecipesFromFirebase(searchTerm, SearchType.ByCuisine)
        .takeIf { it.isNotEmpty() }
        ?: searchRecipesFromFirebase(searchTerm, SearchType.ByType)
            .takeIf { it.isNotEmpty() }
                ?: searchRecipesFromFirebase(searchTerm, SearchType.ByDrinkType)
    Log.d("SearchTerm ", recipes.toString())
    if (recipes.isEmpty()) {
        Text(
            text = "No recipes found",
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxSize()
                .wrapContentHeight(align = Alignment.CenterVertically),
            fontSize = 20.sp,
            color = Color.Gray
        )
    }
    else
    {
        val paddingValue = 80.dp
        // Display the recipes
        RecipePage(recipes = recipes,navController = navController,bottompadding = paddingValue)
    }

    Log.d("Recipes in screen", "" + recipes.size)

}