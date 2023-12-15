package com.example.recipebook

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.recipebook.Lists.CuisineList
import com.example.recipebook.Lists.coursesList
import com.example.recipebook.data.Recipe
import com.example.recipebook.firebaselogic.fetchRecipesFromFirebase


@Composable
fun HomeScreen(navController: NavController) {
    LazyColumn(
        modifier = Modifier.padding(12.dp) ,
        contentPadding = PaddingValues(top=10.dp ,bottom = 70.dp)
    ) {
        item {
            SearchBox(navController)
        }
        item {
//            MainRecipeCard(data = highlightRecipe , navController)
            DisplayHighlightRecipe(navController = navController)
        }
        item {
            DisplayRecipeGrid(navController)
        }
        item {
            Column() {
                Text(
                    "More Categories",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black,
                    modifier = Modifier.padding(
                        start = 20.dp, // Left padding
                        top = 8.dp, // Top padding
                        bottom = 20.dp // Bottom padding
                    )
                )
                CategoryButton(coursesList, navController, 3, 120.dp, 80.dp)
                CategoryButton(CuisineList, navController, 3, 280.dp, 80.dp)

            }
        }
        item {
           Button(modifier = Modifier
                .background(color = Color(0xFFf06d0a)) // Use Color() to specify the color
                .padding(16.dp) // Add some padding for better appearance
                .fillMaxWidth(), // Make the button fill the width of its container
                onClick = { navController.navigate("addRecipe") })
               {
                Text("Become Chef")

            }
        }

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBox(navController: NavController){
    Box(
        modifier = Modifier
            .height(100.dp)
            .fillMaxWidth()
            .padding(top = 16.dp)
            ,
        contentAlignment = Alignment.TopCenter,

    ){
        var searchQuery by remember { mutableStateOf("") }

        // Call the SearchBarM3 composable and pass the lambda function
        SearchBarM3(navController) { newSearchQuery ->
            // Handle the new search query in the calling composable
         searchQuery = newSearchQuery
        }
    }
}


@Composable
fun DisplayRecipeGrid(navController: NavController) {
    var recipes by remember { mutableStateOf<List<Recipe>>(emptyList()) }

    LaunchedEffect(Unit) {
       fetchRecipesFromFirebase(4) { fetchedRecipes ->
            recipes = fetchedRecipes
        }
    }

    RecipePage(recipes = recipes, navController, 2, 450.dp, 2.dp)
}
@Composable
fun DisplayHighlightRecipe(navController: NavController) {
    var recipes by remember { mutableStateOf<List<Recipe>>(emptyList()) }

    LaunchedEffect(Unit) {
        fetchRecipesFromFirebase(1) { fetchedRecipe ->
            recipes = fetchedRecipe
        }
    }

    RecipePage(recipes = recipes, navController, 1, 300.dp, 2.dp,true)
}

