package com.example.recipebook

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.recipebook.data.Recipe
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.*
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class AddFavorites {
    private val database = FirebaseDatabase.getInstance()
    private val usersReference = database.getReference("users")
    private val recipesReference = database.getReference("recipes")
    // Function to add a recipe to user's favorites
    fun addRecipeToFavorites(recipeId: String) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        userId?.let {
            val userFavoritesRef = usersReference.child(it).child("favorites")
            userFavoritesRef.child(recipeId).setValue(true)
        }
    }

    // Function to remove a recipe from user's favorites
    fun removeRecipeFromFavorites(recipeId: String?) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        userId?.let {
            val userFavoritesRef = usersReference.child(it).child("favorites")
            if (recipeId != null) {
                userFavoritesRef.child(recipeId).removeValue()
            }
        }
    }


    fun getCurrentUserId(): String {
        val auth: FirebaseAuth = Firebase.auth
        return auth.currentUser?.uid ?: ""
    }
    val userId= getCurrentUserId()

    suspend fun getFavoriteRecipeIds(): List<String> = suspendCoroutine { continuation ->
        val favoritesRef = usersReference.child(userId).child("favorites")
        Log.d("hhfdhf","$favoritesRef")
        favoritesRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val favoriteRecipeIds = snapshot.children.mapNotNull { childSnapshot ->
                    childSnapshot.key as? String
                }
                Log.d("hhfdhfidss","$favoriteRecipeIds")
                continuation.resume(favoriteRecipeIds)
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
                Log.e("Firebase Error", "Error fetching favorite recipe IDs: ${error.message}")
                continuation.resume(emptyList())
            }
        })
    }


    suspend fun getRecipeById(recipeId: String?): Recipe? = suspendCoroutine { continuation ->
        val recipesRef = recipeId?.let { database.getReference("recipes").child(it) }
        Log.d("FirebaseManager", "Fetching recipe for ID: $recipeId")

        recipesRef?.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                //  Log.d("FirebaseManager", "DataSnapshot: $snapshot")
                Log.d("FirebaseManager", "DataSnapshot key: ${snapshot.key}")
                Log.d("FirebaseManager", "DataSnapshot value: ${snapshot.value}")
                val recipe = snapshot.getValue(Recipe::class.java)
                Log.d("FirebaseManager", "Received recipe from Firebase: $recipe")
                continuation.resume(recipe)
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
                Log.e("FirebaseManager", "Firebase query cancelled or failed: ${error.message}")
                continuation.resume(null)
            }
        })
    }



}

@OptIn(ExperimentalGlideComposeApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FavouriteRecipesScreen(navController: NavController) {
    val addfav = AddFavorites()
    val userId = FirebaseAuth.getInstance().currentUser?.uid

    var favoriteRecipes by remember { mutableStateOf(emptyList<Recipe>()) }

    LaunchedEffect(userId) {
        val favoriteRecipeIds = addfav.getFavoriteRecipeIds()
        val recipes = mutableListOf<Recipe>()

        // Fetch each recipe using the recipeId
        for (recipeId in favoriteRecipeIds) {
            val recipe = addfav.getRecipeById(recipeId)
            recipe?.let { recipes.add(it) }
        }

        favoriteRecipes = recipes
    }

    LazyVerticalGrid(columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)) {
        items(favoriteRecipes) { recipe ->
            RecipeItems(
                navController = navController,
                recipe = recipe,
                onRemoveFromFavorites = {
                    addfav.removeRecipeFromFavorites(recipe.id)
                    // Update the list by recomposing FavouriteRecipesScreen
                    favoriteRecipes = favoriteRecipes.filterNot { removedRecipe ->
                        removedRecipe.id == recipe.id
                    }
                }
            )
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RecipeItems(navController: NavController, recipe: Recipe, onRemoveFromFavorites: () -> Unit) {
    var isLiked by remember { mutableStateOf(false) }
    val addfav = AddFavorites()

    Card(
        modifier = Modifier
            .padding(8.dp)
            .width(180.dp) // Adjust the width as needed
            .clickable {
                // Navigate to recipe details or perform any action
                navController.navigate("videoPage/${recipe.id}")
            },
        shape = MaterialTheme.shapes.medium,
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            ) {
                // Use Glide to load the image
                GlideImage(
                    model = recipe.coverImageUri,
                    contentDescription = recipe.title,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(shape = MaterialTheme.shapes.medium),
                    contentScale = ContentScale.Crop
                )
                val icon: ImageVector = Icons.Default.Clear
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = if (isLiked) Color.Red else Color.Black,
                    modifier = Modifier
                        .size(50.dp)
                        .padding(8.dp)
                        .align(Alignment.TopEnd)
                        .clickable {
                            onRemoveFromFavorites()
                        }
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = recipe.title ?: "",
                fontWeight = FontWeight.Light,
                fontSize = 16.sp,
                color = Color.Black,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
            )
        }
    }
}

