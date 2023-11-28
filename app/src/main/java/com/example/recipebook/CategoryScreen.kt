package com.example.recipebook

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.example.recipebook.data.ItemCategory


val categoryItems = listOf(
    ItemCategory(title = "Breakfast", imgResId = R.drawable.englishbreakfast),
    ItemCategory(title = "Lunch", imgResId = R.drawable.lunch),
    ItemCategory(title = "Dinner", imgResId = R.drawable.christmasdinner),
    ItemCategory(title = "Desserts", imgResId = R.drawable.cupcake),
    ItemCategory(title = "Drinks", imgResId = R.drawable.drink),
    ItemCategory(title = "Breakfast", imgResId = R.drawable.englishbreakfast),
    ItemCategory(title = "Lunch", imgResId = R.drawable.lunch),
    ItemCategory(title = "Dinner", imgResId = R.drawable.christmasdinner),
    ItemCategory(title = "Desserts", imgResId = R.drawable.cupcake),

    // Add more items as needed
)
val coursesList = listOf(
    ItemCategory(title = "Breakfast", imgResId = R.drawable.englishbreakfast),
    ItemCategory(title = "Lunch", imgResId = R.drawable.lunch),
    ItemCategory(title = "Dinner", imgResId = R.drawable.christmasdinner),

    // Add more items as needed
)
val CuisineList = listOf(
    ItemCategory(title = "Breakfast", imgResId = R.drawable.englishbreakfast),
    ItemCategory(title = "Lunch", imgResId = R.drawable.lunch),
    ItemCategory(title = "Dinner", imgResId = R.drawable.christmasdinner),
    ItemCategory(title = "Desserts", imgResId = R.drawable.cupcake),
    ItemCategory(title = "Drinks", imgResId = R.drawable.drink),
    ItemCategory(title = "Breakfast", imgResId = R.drawable.englishbreakfast),
    ItemCategory(title = "Lunch", imgResId = R.drawable.lunch),
    ItemCategory(title = "Dinner", imgResId = R.drawable.christmasdinner),
    ItemCategory(title = "Desserts", imgResId = R.drawable.cupcake),

    // Add more items as needed
)
@Composable
fun CategoryScreen()
{
    Column() {
        CategoryButton(images = categoryItems)
        CategoryButton(images = coursesList)
        CategoryButton(images = CuisineList)
    }
}