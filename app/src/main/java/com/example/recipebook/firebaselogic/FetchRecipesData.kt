package com.example.recipebook.firebaselogic

import android.util.Log
import com.example.recipebook.data.Recipe
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
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
