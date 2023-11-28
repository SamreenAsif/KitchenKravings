package com.example.recipebook


sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: Int,

) {

    object Home : BottomBarScreen(
        route = "home",
        title = "Home",
        icon = R.drawable.restaurant,

    )

    object Recipes : BottomBarScreen(
        route = "recipes",
        title = "Recipes",
        icon = R.drawable.chef1,

    )

    object Categories : BottomBarScreen(
        route = "categories",
        title = "All Categories",
        icon = R.drawable.allcategory,

    )
}
