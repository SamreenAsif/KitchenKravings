package com.example.recipebook.data

data class Category(
    val categoryName: String,
    val subcategories: List<Subcategory> = emptyList()
)
