package com.example.recipebook

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Surface
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.recipebook.data.ItemCategory
import com.example.recipebook.data.recipecard


@Composable
fun HomeScreen() {
    LazyColumn {
        item {
            searchBox()
        }

        item {
            Surface(){
                MainRecipeCard(data = highlightRecipe)
            }

        }
        
        item{
            displayRecipeGrid()
        }
        item{

            CategoryButton(recipeItems , 3 ,400.dp)
        }

        // Add more items as needed
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun searchBox(){
    Box(
        modifier = Modifier.height(100.dp),
        contentAlignment = Alignment.TopCenter
    ){
        SearchBarM3()
    }
}
// component 2 ---------------------------
val highlightRecipe : recipecard =
    recipecard(
        id = 1,
        img = R.drawable.recipe1, // Replace with your actual drawable resource ID
        name = "Spaghetti Bolognese",
        desc = "Classic Italian pasta dish with rich tomato sauce and ground beef."
    )

val recipeList: List<recipecard> = listOf(
    recipecard(
        id = 1,
        img = R.drawable.recipe1, // Replace with your actual drawable resource ID
        name = "Spaghetti Bolognese",
        desc = "Classic Italian pasta dish with rich tomato sauce and ground beef."
    ),
    recipecard(
        id = 1,
        img = R.drawable.recipe1, // Replace with your actual drawable resource ID
        name = "Spaghetti Bolognese",
        desc = "Classic Italian pasta dish with rich tomato sauce and ground beef."
    ),
    recipecard(
        id = 1,
        img = R.drawable.recipe1, // Replace with your actual drawable resource ID
        name = "Spaghetti Bolognese",
        desc = "Classic Italian pasta dish with rich tomato sauce and ground beef."
    ),
    recipecard(
        id = 1,
        img = R.drawable.recipe1, // Replace with your actual drawable resource ID
        name = "Spaghetti Bolognese",
        desc = "Classic Italian pasta dish with rich tomato sauce and ground beef."
    ),
)

@Composable
fun displayRecipeGrid(){
    Surface() {
        RecipePage(recipes = recipeList , 2 , 400.dp)
    }
}
//---------------------------------------Component 3
val recipeItems = listOf(
    ItemCategory(title = "Breakfast", imgResId = R.drawable.englishbreakfast),
    ItemCategory(title = "Lunch", imgResId = R.drawable.lunch),
    ItemCategory(title = "Dinner", imgResId = R.drawable.christmasdinner),
    ItemCategory(title = "Desserts", imgResId = R.drawable.cupcake),
    ItemCategory(title = "Drinks", imgResId = R.drawable.drink),
    ItemCategory(title = "Breakfast", imgResId = R.drawable.englishbreakfast),
    ItemCategory(title = "Lunch", imgResId = R.drawable.lunch),
    ItemCategory(title = "Dinner", imgResId = R.drawable.christmasdinner),
    ItemCategory(title = "Desserts", imgResId = R.drawable.cupcake),

    // Add more items as needed
)


//------------------------------------
@Composable
@Preview
fun HomeScreenPreview() {
    HomeScreen()
}