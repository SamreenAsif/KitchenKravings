package com.example.recipebook.firebaselogic

import android.util.Log
import com.example.recipebook.data.Category
import com.example.recipebook.data.Subcategory
import com.google.firebase.database.FirebaseDatabase

//fun writeCategoryToFirebase(context: Context, categoryName: String) {
//    val database = Firebase.database.reference
//
//    // Write the top-level category to Firebase
//    database.child("category").setValue(Category(categoryName))
//    Toast.makeText(context, "Category '$categoryName' written to Firebase", Toast.LENGTH_SHORT).show()
//}
//
//fun writeCategories ()
//class CategoryInitializer {
//
//    private val database = Firebase.database.reference
//
//    fun initializeCategories() {
//        // Add your predefined categories here
//        val categories = listOf(
//            Category("Courses", listOf("Breakfast", "Lunch", "Dinner")),
//            Category("Meal Types", listOf( "Appetizers","Snacks","Soups", "Salads","Side Dishes")),
//            Category("Proteins", listOf("Meat", "Poultry", "Seafood", "Vegetarian Options")),
//            Category("Cuisines", listOf("Desi","Indian","Italian", "Mexican", "Chinese","French","Thai","English")),
//            Category("Beverages", listOf("Smoothie", "Tea", "Juice","Coffee")),
//            Category("Desserts", listOf( "Pastries","Frozen treats", "Chocolate","Puddings and Custards")),
//            Category("Dietary Preferences", listOf( "Veg","Vegan","Keto" , "Gluten Free" , "Low Carb","Dairy Free")),
//            Category("Kids Friendly", listOf( "Lunchbox","Snacks","Healthy treats")),
//
//        )
//
//        for (category in categories) {
//            writeCategoryToFirebase(category)
//        }
//    }
//
//    private fun writeCategoryToFirebase(category: Category) {
//        // Write the category to Firebase
//        database.child("categories").child(category.categoryName).setValue(category)
//    }
//}

class CategoryInitializer {

    private val database = FirebaseDatabase.getInstance()
//    val reference = database.getReference("recipes")
//
//    private val database = Firebase.database.reference
//    private val storage = Firebase.storage.reference
    private val url = "gs://fir-authentication-bc633.appspot.com/CategoryIcons/"
    fun initializeCategories() {
        // Add your predefined categories here
        val categories = listOf(
            Category("Courses", listOf(
                Subcategory("Breakfast", url+"englishbreakfast.png"),
                Subcategory("Lunch", url+"lunch.png"),
                Subcategory("Dinner", url+"christmasdinner.png")
            )),
//            Category("Meal Types", listOf(
//                Subcategory("Appetizers", url+"appetizers.png"),
//                Subcategory("Snacks", url+"snacks.png"),
//                Subcategory("Soups", url+"soups.png"),
//                Subcategory("Salads", url+"salad.png"),
//            )),
//            Category("Proteins", listOf(
//                Subcategory("Meat", url+"appetizers.png"),
//                Subcategory("Poultry", url+"snacks.png"),
//                Subcategory("Seafood", url+"soups.png"),
//
//            )),
//            Category("Beverages", listOf(
//                Subcategory("Smoothie", url+"appetizers.png"),
//                Subcategory("Tea", url+"snacks.png"),
//                Subcategory("Juice", url+"soups.png"),
//                Subcategory("Coffee", url+"soups.png"),
//                )),
//            Category("Desserts", listOf(
//                Subcategory("Pastries", url+"appetizers.png"),
//                Subcategory("Frozen treats", url+"snacks.png"),
//                Subcategory("Chocolate", url+"soups.png"),
//                Subcategory("Puddings and Custards", url+"soups.png"),
//            )),
//            Category("Dietary Preferences", listOf(
//                Subcategory("Veg", url+"appetizers.png"),
//                Subcategory("Vegan", url+"snacks.png"),
//                Subcategory("Keto", url+"soups.png"),
//                Subcategory("Gluten Free", url+"soups.png"),
//                Subcategory("Low Carb", url+"soups.png"),
//                Subcategory("Dairy Free", url+"soups.png"),
//            )),
//            Category("Kids Friendly", listOf(
//                Subcategory("Lunchbox", url+"appetizers.png"),
//                Subcategory("Snacks", url+"snacks.png"),
//                Subcategory("Healthy treats", url+"soups.png"),
//            )),

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