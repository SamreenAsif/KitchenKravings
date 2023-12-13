package com.example.recipebook.firebaselogic

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.recipebook.data.Recipe
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
@Composable
fun searchRecipesFromFirebase(searchTerm: String, searchType: SearchType): List<Recipe> {
    var recipes by remember { mutableStateOf(emptyList<Recipe>()) }

    DisposableEffect(Unit) {
        val database = FirebaseDatabase.getInstance()
        val reference = database.getReference("recipes")

        val query = when (searchType) {
            SearchType.ByCuisine -> reference.orderByChild("cuisine").equalTo(searchTerm)
            SearchType.ByType -> reference.orderByChild("type").equalTo(searchTerm)
            SearchType.ByDrinkType -> reference.orderByChild("drinkType").equalTo(searchTerm)
        }

        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val newRecipes = snapshot.children.mapNotNull { dataSnapshot ->
                    dataSnapshot.getValue(Recipe::class.java)
                }

                if (newRecipes.isNotEmpty()) {
                    recipes = newRecipes
                    Log.d("Search", recipes.toString())
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle the error
            }
        }

        query.addValueEventListener(listener)

        onDispose {
            query.removeEventListener(listener)
        }
    }

    return recipes
}

enum class SearchType {
    ByCuisine,
    ByType,
    ByDrinkType
}
