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
import com.example.recipebook.data.ItemCategory
import com.example.recipebook.data.Recipe
import com.example.recipebook.data.recipecard
import com.example.recipebook.firebaselogic.FetchRecipesFromFirebase


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
                MainRecipeCard(data = highlightRecipe)
        }
        item{
            DisplayRecipeGrid(navController)
        }
        item{
            Column(){
                Text(
                    "More Categories" ,
                    fontSize  = 20.sp ,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black,
                    modifier = Modifier.padding(
                            start = 20.dp, // Left padding
                            top = 8.dp, // Top padding
                            bottom = 20.dp // Bottom padding
                        )
                )
                CategoryButton(recipeItems ,navController, 3 ,400.dp , 80.dp)
            }
        }
        item{
                Button(onClick = { navController.navigate("addRecipe")}) {
                    Text("Become Chef")
                }
            }

        item{
            Button(onClick = { navController.navigate("searchRecipe")}) {
                Text("Search")
            }
        }
      // test-dropdown
        item{
            Button(onClick = { navController.navigate("test-dropdown")}) {
                Text("DropDown")
            }
        }
//        item{
//            retrievedata()
//        }
        // Add more items as needed
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
//        if (searchQuery.isNotEmpty()){
//            navController.navigate("categoryRecipes/${searchQuery}")
//        }
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

@Composable
fun DisplayRecipeGrid(navController: NavController) {

    var recipes by remember { mutableStateOf(emptyList<Recipe>()) }
    // Fetch recipes from Firebase and update the recipes state
    recipes = FetchRecipesFromFirebase(4)

    RecipePage(recipes = recipes ,navController, 2 , 450.dp,2.dp)
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

