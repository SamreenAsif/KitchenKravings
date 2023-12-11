package com.example.recipebook.firebaselogic

// RecipeRepository.kt

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.rememberCoroutineScope
import com.example.recipebook.Recipe
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

import kotlinx.coroutines.launch

@Composable
fun fetchRecipeById(recipeId: String, onRecipeFetched: (Recipe?) -> Unit) {
    val coroutineScope = rememberCoroutineScope()

    DisposableEffect(Unit) {
        val database = FirebaseDatabase.getInstance()
        val reference = database.getReference("recipes").child(recipeId)

        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val recipe = snapshot.getValue(Recipe::class.java)

                coroutineScope.launch {
                    // Pass the fetched recipe to the callback
                    onRecipeFetched(recipe)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle the error
                coroutineScope.launch {
                    // Pass null to indicate an error
                    onRecipeFetched(null)
                }
            }
        }

        reference.addListenerForSingleValueEvent(listener)

        onDispose {
            reference.removeEventListener(listener)
        }
    }
}
