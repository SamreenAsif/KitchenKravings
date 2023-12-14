package com.example.recipebook.firebaselogic

import android.util.Log
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
//@Composable
//fun FetchRecipesFromFirebase(limit: Int? = null): List<Recipe> {
//    // State to hold the fetched recipes
//    var recipes by remember { mutableStateOf(emptyList<Recipe>()) }
//
//    DisposableEffect(Unit) {
//        val database = FirebaseDatabase.getInstance()
//        val reference = database.getReference("recipes")
//
//        // Add a listener to fetch data based on the specified limit
//        val listener = object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//
//                val fetchedRecipes = snapshot.children
//                    .toList()
//                    .sortedByDescending { it.key } // Sort by key (assuming the key is a timestamp or unique identifier)
//                    .take(limit ?: snapshot.childrenCount.toInt()) // Take specified limit or all recipes if limit is null
//                    .mapNotNull { dataSnapshot ->
//                        dataSnapshot.getValue(Recipe::class.java)
//                    }
//                Log.d("Recipe count" ,""+snapshot.childrenCount.toInt())
//                Log.d("feteched recipe" ,""+fetchedRecipes.toString())
//                recipes = fetchedRecipes
//
//            }
//
//
//            override fun onCancelled(error: DatabaseError) {
//                // Handle the error
//            }
//        }
//
//        // Use limitToLast if fetching the most recent recipes
//        if (limit != null) {
//            reference.limitToLast(limit).addValueEventListener(listener)
//        } else {
//            reference.addValueEventListener(listener)
//        }
//
//        // Cleanup when the effect leaves the composition
//        onDispose {
//            reference.removeEventListener(listener)
//        }
//    }
//
//    return recipes
//}

//fun FetchRecipesFromFirebase(limit: Int? = null, onRecipesFetched: (List<Recipe>) -> Unit) {
//    val database = FirebaseDatabase.getInstance()
//    val reference = database.getReference("recipes")
//
//    // Add a listener to fetch data based on the specified limit
//    val listener = object : ValueEventListener {
//        override fun onDataChange(snapshot: DataSnapshot) {
//
//            val fetchedRecipes = snapshot.children
//                .toList()
//                .sortedByDescending { it.key } // Sort by key (assuming the key is a timestamp or unique identifier)
//                .take(limit ?: snapshot.childrenCount.toInt()) // Take specified limit or all recipes if limit is null
//                .mapNotNull { dataSnapshot ->
//                    dataSnapshot.getValue(Recipe::class.java)
//                }
//
//            Log.d("Recipe count", "" + snapshot.childrenCount.toInt())
//            Log.d("Fetched recipes", "" + fetchedRecipes.toString())
//
//            onRecipesFetched(fetchedRecipes)
//        }
//
//        override fun onCancelled(error: DatabaseError) {
//            // Handle the error
//        }
//    }
//
//    // Use limitToLast if fetching the most recent recipes
//    if (limit != null) {
//        reference.limitToLast(limit).addValueEventListener(listener)
//    } else {
//        reference.addValueEventListener(listener)
//    }
//
//    // Cleanup when the effect leaves the composition
//    // Note: You may need to manage the lifecycle and cleanup externally
//
//    // For example, you can store a reference to the listener and remove it when needed:
//    // reference.removeEventListener(listener)
//}

fun fetchRecipesFromFirebase(
    limit: Int? = null,
    onRecipesFetched: (List<Recipe>) -> Unit
): ValueEventListener {
    val database = FirebaseDatabase.getInstance()
    val reference = database.getReference("recipes")

    // Add a listener to fetch data based on the specified limit
    val listener = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            val fetchedRecipes = snapshot.children
                .toList()
                .sortedByDescending { it.key }
                .take(limit ?: snapshot.childrenCount.toInt())
                .mapNotNull { dataSnapshot ->
                    dataSnapshot.getValue(Recipe::class.java)
                }

            Log.d("Recipe count", "" + snapshot.childrenCount.toInt())
            Log.d("Fetched recipes", "" + fetchedRecipes.toString())

            onRecipesFetched(fetchedRecipes)
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

    return listener
}
