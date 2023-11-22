package com.example.recipebook

import androidx.compose.runtime.Composable


val imageResourceIds = listOf(
    R.drawable.englishbreakfast,
    R.drawable.lunch,
    R.drawable.christmasdinner,
    R.drawable.cupcake,
    R.drawable.drink,
    // Add more resource IDs as needed
)
@Composable
fun SettingScreen()
{
    CategoryButton(images = imageResourceIds)
}