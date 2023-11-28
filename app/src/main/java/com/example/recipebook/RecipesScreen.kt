package com.example.recipebook

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.recipebook.data.recipecard

@Composable
fun RecipesScreen(navController: NavController)
{
    RecipePage(recipes = recipes , navController)
}

val recipes: List<recipecard> = listOf(
    recipecard(
        id = 1,
        img = R.drawable.recipe1, // Replace with your actual drawable resource ID
        name = "Spaghetti Bolognese",
        desc = "Classic Italian pasta dish with rich tomato sauce and ground beef."
    ),
    recipecard(

        id = 2,
        img = R.drawable.recipe3, // Replace with your actual drawable resource ID
        name = "Chicken Caesar Salad",
        desc = "Fresh salad with grilled chicken, crispy croutons, and Caesar dressing."
    ),
    recipecard(

        id = 2,
        img = R.drawable.recipe2, // Replace with your actual drawable resource ID
        name = "Chicken Caesar Salad",
        desc = "Fresh salad with grilled chicken, crispy croutons, and Caesar dressing."
    ),
    recipecard(

        id = 2,
        img = R.drawable.recipe2, // Replace with your actual drawable resource ID
        name = "Chicken Caesar Salad",
        desc = "Fresh salad with grilled chicken, crispy croutons, and Caesar dressing."
    ),
    recipecard(

        id = 2,
        img = R.drawable.recipe3, // Replace with your actual drawable resource ID
        name = "Chicken Caesar Salad",
        desc = "Fresh salad with grilled chicken, crispy croutons, and Caesar dressing."
    ),
    recipecard(

        id = 2,
        img = R.drawable.recipe3, // Replace with your actual drawable resource ID
        name = "Chicken Caesar Salad",
        desc = "Fresh salad with grilled chicken, crispy croutons, and Caesar dressing."
    ),
    recipecard(

        id = 2,
        img = R.drawable.recipe3, // Replace with your actual drawable resource ID
        name = "Chicken Caesar Salad",
        desc = "Fresh salad with grilled chicken, crispy croutons, and Caesar dressing."
    ),
    recipecard(

        id = 2,
        img = R.drawable.recipe3, // Replace with your actual drawable resource ID
        name = "Chicken Caesar Salad",
        desc = "Fresh salad with grilled chicken, crispy croutons, and Caesar dressing."
    ),
    recipecard(

        id = 2,
        img = R.drawable.recipe3, // Replace with your actual drawable resource ID
        name = "Chicken Caesar Salad",
        desc = "Fresh salad with grilled chicken, crispy croutons, and Caesar dressing."
    ),
    // Add more recipe cards as needed
)