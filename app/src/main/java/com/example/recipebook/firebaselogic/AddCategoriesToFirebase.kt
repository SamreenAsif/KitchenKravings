package com.example.recipebook.firebaselogic

import android.util.Log
import com.example.recipebook.data.Category
import com.example.recipebook.data.Subcategory
import com.google.firebase.database.FirebaseDatabase

class CategoryInitializer {

    private val database = FirebaseDatabase.getInstance()
    private val url = "gs://fir-authentication-bc633.appspot.com/CategoryIcons/"
    fun initializeCategories() {
        val categories = listOf(
            Category("Courses", listOf(
                Subcategory("Breakfast", url+"englishbreakfast.png"),
                Subcategory("Lunch", url+"lunch.png"),
                Subcategory("Dinner", url+"christmasdinner.png")
            )),
                    )

        for (category in categories) {
            writeCategoryToFirebase(category)
        }
    }

    private fun writeCategoryToFirebase(category: Category) {
        // Write the category to Firebase
        try {
            // Write the category to Firebase
            database.getReference("categories").child(category.categoryName).setValue(category)
        } catch (e: Exception) {
            Log.e("FirebaseWriteError", "Error writing category to Firebase", e)
        }
    }
}