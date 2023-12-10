package com.example.recipebook

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import com.example.recipebook.data.recipecard
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


@Composable
fun RecipesScreen(navController: NavController) {
    // State to hold the list of recipes
    var recipes by remember { mutableStateOf(emptyList<Recipe>()) }

    // Function to fetch recipes from Firebase
    LaunchedEffect(key1 = Unit) {
        val database = FirebaseDatabase.getInstance()
        val reference = database.getReference("recipes")

        // Add a listener to fetch data whenever it changes
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val newRecipes = snapshot.children.mapNotNull { dataSnapshot ->
                    dataSnapshot.getValue(Recipe::class.java)
                }
                recipes = newRecipes
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle the error
            }
        }

        reference.addValueEventListener(listener)
    }

    // Display the recipes
    RecipePage(recipes = recipes, navController)
}

//
//@Composable
//fun RecipesScreen(navController: NavController)
//{
//    RecipePage(recipes = recipes , navController)
//}
//
//val recipes: List<recipecard> = listOf(
//    recipecard(
//        id = 1,
//        img = R.drawable.recipe1, // Replace with your actual drawable resource ID
//        name = "Spaghetti Bolognese",
//        desc = "Classic Italian pasta dish with rich tomato sauce and ground beef."
//    ),
//    recipecard(
//
//        id = 2,
//        img = R.drawable.recipe3, // Replace with your actual drawable resource ID
//        name = "Chicken Caesar Salad",
//        desc = "Fresh salad with grilled chicken, crispy croutons, and Caesar dressing."
//    ),
//    recipecard(
//
//        id = 2,
//        img = R.drawable.recipe2, // Replace with your actual drawable resource ID
//        name = "Chicken Caesar Salad",
//        desc = "Fresh salad with grilled chicken, crispy croutons, and Caesar dressing."
//    ),
//    recipecard(
//
//        id = 2,
//        img = R.drawable.recipe2, // Replace with your actual drawable resource ID
//        name = "Chicken Caesar Salad",
//        desc = "Fresh salad with grilled chicken, crispy croutons, and Caesar dressing."
//    ),
//    recipecard(
//
//        id = 2,
//        img = R.drawable.recipe3, // Replace with your actual drawable resource ID
//        name = "Chicken Caesar Salad",
//        desc = "Fresh salad with grilled chicken, crispy croutons, and Caesar dressing."
//    ),
//    recipecard(
//
//        id = 2,
//        img = R.drawable.recipe3, // Replace with your actual drawable resource ID
//        name = "Chicken Caesar Salad",
//        desc = "Fresh salad with grilled chicken, crispy croutons, and Caesar dressing."
//    ),
//    recipecard(
//
//        id = 2,
//        img = R.drawable.recipe3, // Replace with your actual drawable resource ID
//        name = "Chicken Caesar Salad",
//        desc = "Fresh salad with grilled chicken, crispy croutons, and Caesar dressing."
//    ),
//    recipecard(
//
//        id = 2,
//        img = R.drawable.recipe3, // Replace with your actual drawable resource ID
//        name = "Chicken Caesar Salad",
//        desc = "Fresh salad with grilled chicken, crispy croutons, and Caesar dressing."
//    ),
//    recipecard(
//
//        id = 2,
//        img = R.drawable.recipe3, // Replace with your actual drawable resource ID
//        name = "Chicken Caesar Salad",
//        desc = "Fresh salad with grilled chicken, crispy croutons, and Caesar dressing."
//    ),
//    // Add more recipe cards as needed
//)