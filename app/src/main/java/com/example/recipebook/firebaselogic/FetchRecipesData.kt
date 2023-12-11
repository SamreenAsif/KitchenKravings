package com.example.recipebook.firebaselogic

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.recipebook.Recipe
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

@Composable
fun FetchRecipesFromFirebase(): List<Recipe> {
    // State to hold the fetched recipes
    var recipes by remember { mutableStateOf(emptyList<Recipe>()) }

    DisposableEffect(Unit) {
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

        // Cleanup when the effect leaves the composition
        onDispose {
            reference.removeEventListener(listener)
        }
    }

    return recipes
}

