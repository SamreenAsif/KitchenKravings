package com.example.recipebook


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.recipebook.presentation.GoogleSignInManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    navController : NavHostController,
    scrollBehavior : TopAppBarScrollBehavior,
    googleSignInManager: GoogleSignInManager?,
    onNavigationIconClick : () -> Unit
) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    // Get the current destination
    val currentDestination = navBackStackEntry?.destination?.route
    val username = googleSignInManager?.profileInfo?.displayName ?: "Guest"

// Determine the title based on the current destination
    val title = when (currentDestination) {
        BottomBarScreen.Home.route -> "Hello $username!"
        BottomBarScreen.Recipes.route -> BottomBarScreen.Recipes.title
        BottomBarScreen.Categories.route -> BottomBarScreen.Categories.title
        else -> "Hello, Guest!" // Default title
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
                    contentDescription = "Menu bar",
                    tint =  Color.White,
                    modifier = Modifier
                        .size(30.dp)
                )
            }
        },
        title =
        {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title ?: "",
                    fontWeight = FontWeight.Light,
                    fontSize = 20.sp,
                    color = Color.White,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp)) // Adjust the spacing as needed
                val icon: ImageVector = Icons.Default.Favorite
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Color.White ,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable {
                            navController.navigate("FavouriteRecipesScreen")
                        }
                    )
            }
        },
        scrollBehavior = scrollBehavior,
    )
}