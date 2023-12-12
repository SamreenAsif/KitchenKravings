package com.example.recipebook

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.recipebook.data.Recipe
import com.example.recipebook.firebaselogic.fetchRecipeById

@Composable
fun RecipeDetailsScreen(navController: NavController, recipeId: String?) {

    var recipe by remember { mutableStateOf<Recipe?>(null) }

    // Fetch the recipe by ID
    if (recipeId != null) {
        fetchRecipeById(recipeId) { fetchedRecipe ->
            // Update the state with the fetched recipe
            recipe = fetchedRecipe
        }
    }
    else
        Toast.makeText(LocalContext.current , "Recipe not found" , Toast.LENGTH_LONG).show()

    // Display the recipe details if recipe found
    recipe?.let { RecipeDetails(navController,it) }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeDetails(navController: NavController ,recipe :Recipe) {

    var isLiked by remember { mutableStateOf(false) }
    var shareClicked by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Scaffold(
        containerColor = Color.White,
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color.Black,
                ),
                title = {},
                navigationIcon = {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            modifier = Modifier.clickable {
                                navController.popBackStack()
                            },
                            tint = Color.Black
                        )

                    },
                actions = {
                    // Share icon
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = "Share",
                        modifier = Modifier
                            .padding(end = 16.dp)
                            .clickable {
                                shareClicked = true
                            },
                        tint = Color.Black
                    )

                    // Like (heart) icon
                    Icon(
                        imageVector = if (isLiked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Like",
                        modifier = Modifier
                            .padding(end = 16.dp)
                            .clickable {
                                isLiked = !isLiked
                                // Handle like button click if needed
                            }
                        ,
                        tint = Color.Black
                    )
                }
            )
        },
        content = {
            PageContent(recipe)
        },

    )
            // Launch the effect when the share icon is clicked
    LaunchedEffect(shareClicked) {
        if (shareClicked) {
            shareOnWhatsApp(context)
            shareClicked = false // Reset the state
        }
    }

}

@Composable
fun PageContent(recipe: Recipe){

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Ingredients list

        item {
            recipe.title?.let {
                Text(
                    text = it,
                    modifier = Modifier
                        .fillMaxWidth(),
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }
        }

        // Video
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(MaterialTheme.shapes.medium)
            ) {
                if (recipe.videoUri != null) {
                    VideoPlayerScreen(url = recipe.videoUri)
                }
                else
                    Toast.makeText(LocalContext.current , "Can't play video" , Toast.LENGTH_LONG).show()
            }
        }

        // Ingredients list
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Italian Double Cheese Pizza",
                modifier = Modifier
                    .fillMaxWidth(),
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        }

        // Coupon for Ingredients
        item {
            Spacer(modifier = Modifier.height(16.dp))
            recipe.ingredients?.let {
                Coupon(
                    title = "Ingredients",
                    points = it
                )
            }
        }

        // Coupon for Directions
        item {
            Spacer(modifier = Modifier.height(16.dp))
            recipe.directions?.let {
                Coupon(
                    title = "Directions",
                    points = it
                )
            }
        }

        // Add to Grocery List button
        item {
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = {
                    // Handle "Add to Grocery List" button click
                },
                modifier = Modifier
                   .width(190.dp)
            ) {
                Text("Add to Grocery List")
            }
        }
    }

}

@Composable
fun Coupon(title: String, points: List<String>) {
    Column(
        modifier = Modifier
            .border(
                border = BorderStroke(5.dp, Color.LightGray),
                shape = MaterialTheme.shapes.medium
            )
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Text(text = title, color = Color.Black)

        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            points.forEach { point ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "•", color = Color.Black)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = point, color = Color.Black)
                }
            }
        }
    }
}

