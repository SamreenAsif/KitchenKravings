package com.example.recipebook.firebaselogic

import com.example.recipebook.data.Recipe
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
enum class SearchType {
    ByCuisine,
    ByType,
    ByDrinkType
}

suspend fun searchRecipesFromFirebase(
    searchTerm: String,
    searchType: SearchType
): List<Recipe> = withContext(Dispatchers.IO) {
    val database = FirebaseDatabase.getInstance()
    val reference = database.getReference("recipes")

    val query = when (searchType) {
        SearchType.ByCuisine -> reference.orderByChild("cuisine").equalTo(searchTerm)
        SearchType.ByType -> reference.orderByChild("type").equalTo(searchTerm)
        SearchType.ByDrinkType -> reference.orderByChild("drinkType").equalTo(searchTerm)
    }

    val snapshot = query.get().await()

    val newRecipes = snapshot.children.mapNotNull { dataSnapshot ->
        dataSnapshot.getValue(Recipe::class.java)
    }

    // Don't forget to handle potential errors

    newRecipes
}
suspend fun searchRecipesById(
): List<Recipe> = withContext(Dispatchers.IO) {
    val userId = FirebaseAuth.getInstance().currentUser?.uid
    val database = FirebaseDatabase.getInstance()
    val reference = database.getReference("recipes")

    val query =
        reference.orderByChild("chefId").equalTo(userId)


    val snapshot = query.get().await()

    val newRecipes = snapshot.children.mapNotNull { dataSnapshot ->
        dataSnapshot.getValue(Recipe::class.java)
    }

    // Don't forget to handle potential errors

    newRecipes
}