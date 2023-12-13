package com.example.recipebook.data

import java.io.Serializable

data class Recipe(
    var id: String? = null,
    val title: String? = null,
    val coverImageUri: String? = null,
    val description: String? = null,
    val cookingTime: String? = null,
    val servings: String? = null,
    val videoUri: String? = null,
    val ingredients: List<String>? = null,
    val directions: List<String>? = null,
//    val category: List<String>? = null
    val type : String? = null,
    val cuisine : String? = null ,
    val drinkType : String? = null ,
) : Serializable
