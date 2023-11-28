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
                            icon = R.drawable.courses
                        ),
                        MenuItem(
                            id = "cuisine",
                            title = "Cuisine",
                            contentDescription = "Get help",
                            icon = R.drawable.cuisine
                        ),
                        MenuItem(
                            id = "vegetarian",
                            title = "Vegetarian",
                            contentDescription = "Get help",
                            icon = R.drawable.vegan
                        ),
                        MenuItem(
                            id = "non-veg",
                            title = "Non-Veg",
                            contentDescription = "Get help",
                            icon = R.drawable.nonveg

                        ),
                        MenuItem(
                            id = "healthy",
                            title = "Healthy",
                            contentDescription = "Get help",
                            icon = R.drawable.healthyfood1
                        ),
                        MenuItem(
                            id = "bakery",
                            title = "Bakery",
                            contentDescription = "Get help",
                            icon = R.drawable.bakery2
                        ),
                        MenuItem(
                            id = "desserts",
                            title = "Desserts",
                            contentDescription = "Get help",
                            icon = R.drawable.sweets
                        ),
                        MenuItem(
                            id = "logout",
                            title = "Logout",
                            contentDescription = "Get help",
                            icon = R.drawable.logout2
                        ),
                    ),
                    onItemClick = { menuItem ->
                        when (menuItem.id) {
                            "categories" -> navController.navigate(BottomBarScreen.Categories.route)
//                            "courses" -> navController.navigate(Screen.Courses.route)
//                            "cuisine" -> navController.navigate(Screen.Cuisine.route)
                            // Repeat the above for other menu items
//                            "logout" -> navController.navigate(Screen.Logout.route)
                        }
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
        BottomBarScreen.Recipes,
        BottomBarScreen.Categories,
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

