package com.example.recipebook.firebaselogic

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.example.recipebook.data.Recipe
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

//@Composable
//fun FetchRecipesFromFirebase(): List<Recipe> {
//    // State to hold the fetched recipes
//    var recipes by remember { mutableStateOf(emptyList<Recipe>()) }
//
//    DisposableEffect(Unit) {
//        val database = FirebaseDatabase.getInstance()
//        val reference = database.getReference("recipes")
//
//        // Add a listener to fetch data whenever it changes
//        val listener = object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                val newRecipes = snapshot.children.mapNotNull { dataSnapshot ->
//                    dataSnapshot.getValue(Recipe::class.java)
//                }
//                recipes = newRecipes
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                // Handle the error
//            }
//        }
//
//        reference.addValueEventListener(listener)
//
//        // Cleanup when the effect leaves the composition
//        onDispose {
//            reference.removeEventListener(listener)
//        }
//    }
//
//    return recipes
//}
//
@Composable
fun FetchRecipesFromFirebase(limit: Int? = null): List<Recipe> {
    // State to hold the fetched recipes
    var recipes by remember { mutableStateOf(emptyList<Recipe>()) }

    DisposableEffect(Unit) {
        val database = FirebaseDatabase.getInstance()
        val reference = database.getReference("recipes")

        // Add a listener to fetch data based on the specified limit
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                val fetchedRecipes = snapshot.children
                    .toList()
                    .sortedByDescending { it.key } // Sort by key (assuming the key is a timestamp or unique identifier)
                    .take(limit ?: snapshot.childrenCount.toInt()) // Take specified limit or all recipes if limit is null
                    .mapNotNull { dataSnapshot ->
                        dataSnapshot.getValue(Recipe::class.java)
                    }
                Log.d("Recipe count" ,""+snapshot.childrenCount.toInt())
                Log.d("feteched recipe" ,""+fetchedRecipes.toString())
                recipes = fetchedRecipes

            }


            override fun onCancelled(error: DatabaseError) {
                // Handle the error
            }
        }

        // Use limitToLast if fetching the most recent recipes
        if (limit != null) {
            reference.limitToLast(limit).addValueEventListener(listener)
        } else {
            reference.addValueEventListener(listener)
        }

        // Cleanup when the effect leaves the composition
        onDispose {
            reference.removeEventListener(listener)
        }
    }

    return recipes
}
