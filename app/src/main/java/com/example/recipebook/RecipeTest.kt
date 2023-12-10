package com.example.recipebook

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

import com.google.firebase.database.*
import java.util.*


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeListScreen() {
    // State for managing recipes
    var recipesState by remember { mutableStateOf(emptyList<Recipe>()) }

    // Function to fetch recipes from Firebase
    LaunchedEffect(key1 = Unit) {
        // Initialize Firebase Realtime Database

        // Initialize Firebase Realtime Database
        val database = FirebaseDatabase.getInstance()
        val reference = database.getReference("recipes")

        // Add a listener to fetch data whenever it changes
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val newRecipes = snapshot.children.mapNotNull { dataSnapshot ->
                    dataSnapshot.getValue(Recipe::class.java)
                }
                recipesState = newRecipes
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle the error
            }
        }

        reference.addValueEventListener(listener)

    }

    // Main UI
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Recipe List") },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            // Handle back button click
                        }
                    ) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        content = {innerPadding ->
            RecipeList(recipes = recipesState , modifier = Modifier.padding(innerPadding))
        }
    )
}

@Composable
fun RecipeList(recipes: List<Recipe>, modifier: Modifier) {
    LazyColumn {
        items(recipes) { recipe ->
            RecipeItem(recipe = recipe)
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun RecipeItem(recipe: Recipe) {
    Card(

        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                // Handle item click, e.g., navigate to recipe details screen
            }
            .padding(16.dp),

    ) {
        Column {
            // Display the recipe details
            // You can customize this based on your requirements
            Text(text = "Title: ${recipe.title}", fontWeight = FontWeight.Bold)
            Text(text = "Description: ${recipe.description}")
            Text(text = "Cooking Time: ${recipe.cookingTime}")
            Text(text = "Servings: ${recipe.servings}")
            Text(text = "Category: ${recipe.category}")

//            // Display cover image
//            Image(
//                painter = painterResource(id = R.drawable.cuisine),
//                contentDescription = null,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(200.dp)
//                    .clip(shape = MaterialTheme.shapes.medium)
//            )
        }
    }
}


