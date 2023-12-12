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
fun searchRecipesFromFirebase(searchTerm: String): List<Recipe> {
    // State to hold the fetched recipes
    var recipes by remember { mutableStateOf(emptyList<Recipe>()) }

    DisposableEffect(Unit) {
        val database = FirebaseDatabase.getInstance()
        val reference = database.getReference("recipes")

        // Query recipes based on title containing the search term
        val query = reference.orderByChild("category").equalTo(searchTerm)
        Log.d("search term" , searchTerm)
//        val query = reference.orderByChild("title").startAt(searchTerm).endAt(searchTerm + "\uf8ff")

        // Add a listener to fetch data whenever it changes
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val newRecipes = snapshot.children.mapNotNull { dataSnapshot ->
                    dataSnapshot.getValue(Recipe::class.java)
                }

                recipes = newRecipes
                Log.d("search" , recipes.toString())
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle the error
            }
        }

        query.addValueEventListener(listener)

        // Cleanup when the effect leaves the composition
        onDispose {
            query.removeEventListener(listener)
        }
    }

    return recipes
}
//@Composable
//fun SearchRecipesFromFirebase(searchTerm: String): List<Recipe> {
//    // State to hold the fetched recipes
//    var recipes by remember { mutableStateOf(emptyList<Recipe>()) }
//
//    DisposableEffect(Unit) {
//        val database = FirebaseDatabase.getInstance()
//        val reference = database.getReference("recipes")
//
//        // Query recipes based on title containing the search term
//        val titleQuery = reference.orderByChild("title").startAt(searchTerm).endAt(searchTerm + "\uf8ff")
////        val titleQuery = reference.orderByChild("title").
//        Log.d("title query" , titleQuery.toString())
//
//
//        // Query recipes based on category exactly matching the search term
//        val categoryQuery = reference.orderByChild("category").equalTo(searchTerm)
//        Log.d("category query" , categoryQuery.toString())
//
//        // Add a listener to fetch data whenever it changes
//        val listener = object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                val titleMatches = snapshot.child("title").children.mapNotNull { dataSnapshot ->
//                    dataSnapshot.getValue(Recipe::class.java)
//                }
//                Log.d("title" , titleMatches.toString())
//
//                val categoryMatches = snapshot.child("category").children.mapNotNull { dataSnapshot ->
//                    dataSnapshot.getValue(Recipe::class.java)
//                }
//                Log.d("category" , categoryMatches.toString())
//
//                // Merge the results locally
//                recipes = (titleMatches + categoryMatches).distinctBy { it.id }
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                // Handle the error
//            }
//        }
//
//        titleQuery.addValueEventListener(listener)
//        categoryQuery.addValueEventListener(listener)
//
//        // Cleanup when the effect leaves the composition
//        onDispose {
//            titleQuery.removeEventListener(listener)
//            categoryQuery.removeEventListener(listener)
//        }
//    }
//
//    return recipes
//}
