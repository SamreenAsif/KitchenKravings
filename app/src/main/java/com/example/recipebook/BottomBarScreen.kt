package com.example.recipebook

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource

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

    object Profile : BottomBarScreen(
        route = "recipes",
        title = "Recipes",
        icon = R.drawable.chef1,

    )

    object Settings : BottomBarScreen(
        route = "categories",
        title = "All Categories",
        icon = R.drawable.allcategory,

    )
}
