package com.example.recipebook
import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        scaffoldState = scaffoldState,
        topBar = {
            AppBar(
                navController , scrollBehavior ,
                onNavigationIconClick = {
                    scope.launch {
                        scaffoldState.drawerState.open()
                    }
                }
            )
        },
        drawerGesturesEnabled = scaffoldState.drawerState.isOpen,
        drawerContent = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(android.graphics.Color.parseColor("#f78c3b")),) // Set the desired background color
            ) {
                DrawerHeader()
                DrawerBody(
                    items = listOf(
                        MenuItem(
                            id = "categories",
                            title = "All Categories",
                            contentDescription = "Go to all categories",
                            icon = R.drawable.allcategory
                        ),
                        MenuItem(
                            id = "courses",
                            title = "Courses",
                            contentDescription = "Go to settings screen",
                            icon = R.drawable.cooking
                        ),
                        MenuItem(
                            id = "cuisine",
                            title = "Cuisine",
                            contentDescription = "Get help",
                            icon = R.drawable.cooking
                        ),
                        MenuItem(
                            id = "vegetarian",
                            title = "Vegetarian",
                            contentDescription = "Get help",
                            icon = R.drawable.cooking
                        ),
                        MenuItem(
                            id = "non-veg",
                            title = "Non-Veg",
                            contentDescription = "Get help",
                            icon = R.drawable.cooking
                        ),
                        MenuItem(
                            id = "healthy",
                            title = "Healthy",
                            contentDescription = "Get help",
                            icon = R.drawable.cooking
                        ),
                        MenuItem(
                            id = "bakery",
                            title = "Bakery",
                            contentDescription = "Get help",
                            icon = R.drawable.cupcake
                        ),
                        MenuItem(
                            id = "desserts",
                            title = "Desserts",
                            contentDescription = "Get help",
                            icon = R.drawable.cupcake
                        ),
                        MenuItem(
                            id = "logout",
                            title = "Logout",
                            contentDescription = "Get help",
                            icon = R.drawable.recipe1
                        ),
                    ),

                    onItemClick = {
                        println("Clicked on ${it.title}")
                    }
                )
            }
        } ,
        bottomBar = { BottomBar(navController = navController) }
    )
    { innerPadding ->
        BottomNavGraph(navController = navController, modifier = Modifier.padding(innerPadding))
    }
}


@Composable
fun BottomBar(navController: NavHostController) {
    val screens = listOf(
        BottomBarScreen.Home,
        BottomBarScreen.Profile,
        BottomBarScreen.Settings,
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    BottomNavigation (
        modifier = Modifier.height(70.dp) ,
        backgroundColor = Color(android.graphics.Color.parseColor("#f06d0a"))
    ){
        screens.forEach { screen ->
            AddItem(
                screen = screen,
                currentDestination = currentDestination,
                navController = navController
            )
        }
    }
}


@Composable
fun RowScope.AddItem(
    screen: BottomBarScreen,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    BottomNavigationItem(
        label = {
            Text(text = screen.title)
        },
        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
        unselectedContentColor = LocalContentColor.current.copy(alpha = ContentAlpha.disabled),
        icon = {

            Icon(
                painter = painterResource(id = screen.icon),
                contentDescription = "Navigation Icon",
                modifier = Modifier.size(35.dp),
            )
        },
        onClick = {
            navController.navigate(screen.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        },
    )
}

