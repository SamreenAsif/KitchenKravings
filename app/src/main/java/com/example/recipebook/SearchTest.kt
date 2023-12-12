package com.example.recipebook

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
import com.example.recipebook.firebaselogic.searchRecipesFromFirebase

@Composable
fun SearchTest(navController: NavController){
    var recipes by remember { mutableStateOf(emptyList<Recipe>()) }

    recipes  = searchRecipesFromFirebase(searchTerm = "Chinese")
        if (recipes.isEmpty()) {
            Text(
                text = "No recipes found",
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentHeight(align = Alignment.CenterVertically)
                ,
                fontSize = 20.sp, // Adjust the font size as needed
                color = Color.Gray //
            )
        }
    else
        RecipePage(recipes = recipes, navController = navController, bottompadding = 80.dp)
}