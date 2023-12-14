package com.example.recipebook

import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun SearchTest(navController: NavController) {
//    var recipes by remember { mutableStateOf(emptyList<Recipe>()) }
//
//    recipes = searchRecipesFromFirebase("Indian", SearchType.ByCuisine)
//        .takeIf { it.isNotEmpty() }
//        ?: searchRecipesFromFirebase("Indian", SearchType.ByType)
//            .takeIf { it.isNotEmpty() }
//        ?: searchRecipesFromFirebase("Indian", SearchType.ByDrinkType)
//
//    if (recipes.isEmpty()) {
//        Text(
//            text = "No recipes found",
//            textAlign = TextAlign.Center,
//            modifier = Modifier
//                .fillMaxSize()
//                .wrapContentHeight(align = Alignment.CenterVertically),
//            fontSize = 20.sp,
//            color = Color.Gray
//        )
//    } else {
//        RecipePage(recipes = recipes, navController = navController, bottompadding = 80.dp)
//    }
}

