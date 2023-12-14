package com.example.recipebook

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.concurrent.CompletableFuture
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


class ShoppingList {

    val userId = FirebaseAuth.getInstance().currentUser?.uid
    private val database = FirebaseDatabase.getInstance()
    private val usersReference = database.getReference("users")
    fun addIngredientToShoppingList(ingredient: String) {
        val userShoppingListRef = userId?.let { usersReference.child(it).child("shoppingList") }
        if (userShoppingListRef != null) {
            val uniqueShoppingSet = mutableStateOf<HashSet<String>>(hashSetOf())

            val fetchShoppingListFuture = CompletableFuture<Void>()

            // Fetch the existing shopping list and update uniqueShoppingSet
            userShoppingListRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    uniqueShoppingSet.value.addAll(dataSnapshot.children.mapNotNull { it.getValue(String::class.java) })
                    fetchShoppingListFuture.complete(null)
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    fetchShoppingListFuture.completeExceptionally(databaseError.toException())
                }
            })

            // Wait for the fetchShoppingListFuture to complete before checking for duplicates
            fetchShoppingListFuture.thenAccept {
                if (ingredient !in uniqueShoppingSet.value) {
                    val newIngredientRef = userShoppingListRef.push()

                    newIngredientRef.setValue(ingredient)

                    // Update uniqueShoppingSet to include the newly added ingredient
                    uniqueShoppingSet.value.add(ingredient)
                } else {
                    Log.d("Ingredient already exists", "Ingredient: $ingredient already exists in the shopping list.")
                }
            }
        }
    }

    // Function to remove an ingredient from user's shopping list
    fun removeIngredientFromShoppingList(ingredientId: String) {
        val userShoppingListRef = userId?.let { usersReference.child(it).child("shoppingList") }


        if (userShoppingListRef != null) {
            userShoppingListRef.orderByValue().equalTo(ingredientId).addListenerForSingleValueEvent(
                object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        for (snapshot in dataSnapshot.children) {
                            val ingredientKey = snapshot.key
                            // Now you have the key associated with the value "dough"
                            println("Ingredient Key: $ingredientKey")

                            // Remove the ingredient from the database using the key
                            snapshot.ref.removeValue()
                        }
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        println("Error: ${databaseError.toException()}")
                    }
                }
            )
        }

    }



        // Function to get the user's shopping list
        suspend fun getShoppingList(): List<String> = suspendCoroutine { continuation ->
            val shoppingListRef = userId?.let { usersReference.child(it).child("shoppingList") }

            if (shoppingListRef != null) {
                shoppingListRef.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val shoppingList = snapshot.children.mapNotNull { childSnapshot ->
                            childSnapshot.value as? String
                        }
                        continuation.resume(shoppingList)
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Log.e("Firebase Error", "Error fetching shopping list: ${error.message}")
                        continuation.resume(emptyList())
                    }
                })
            }
        }
    }
