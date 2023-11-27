package com.example.recipebook

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.recipebook.BottomBarScreen.Home.title

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    navController : NavHostController,
    scrollBehavior : TopAppBarScrollBehavior,
    onNavigationIconClick : () -> Unit
){

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    // Get the current destination
    val currentDestination = navBackStackEntry?.destination?.route

    // Determine the title based on the current destination
    val title = when (currentDestination) {
        BottomBarScreen.Home.route -> "Hello,Guest!"
        BottomBarScreen.Profile.route -> BottomBarScreen.Profile.title
        BottomBarScreen.Settings.route -> BottomBarScreen.Settings.title
        else -> "Hello,Guest!" // Default title
    }
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color(android.graphics.Color.parseColor("#f06d0a")),
            titleContentColor = Color.White,
        ),
        navigationIcon = {
            IconButton(
                onClick = onNavigationIconClick ,
            ) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Localized description",
                    tint =  Color.White
                )
            }
        },

        title = {
            Text(
                text = title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = Color.White,
                fontSize = 20.sp,

                )
        },
        scrollBehavior = scrollBehavior,
    )
}
