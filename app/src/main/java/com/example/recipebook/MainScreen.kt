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
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.recipebook.data.Recipe
import com.example.recipebook.firebaselogic.SearchType
import com.example.recipebook.firebaselogic.searchRecipesFromFirebase
import com.example.recipebook.presentation.GoogleSignInManager
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(googleSignInManager: GoogleSignInManager?, myNavController: NavController, signedIn:Boolean) {
    val navController = rememberNavController()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        scaffoldState = scaffoldState,
        topBar = {
            AppBar(
                navController , scrollBehavior ,googleSignInManager,
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
                        .width(100.dp)
                        .background(Color(android.graphics.Color.parseColor("#f78c3b")),) // Set the desired background color
                ) {
                    DrawerHeader()
                    DrawerBody(
                        items = listOf(
                            MenuItem(
                                id = "categories",
                                title = "All Categories",
                                contentDescription = "Go to all categories",
//                                icon = R.drawable.allcategory
                            ),
                            MenuItem(
                                id = "All Recipes",
                                title = "All Recipes",
                                contentDescription = "all recipes",
//                                icon = R.drawable.chef
                            ),
                            MenuItem(
                                id = "courses",
                                title = "Courses",
                                contentDescription = "Go to settings screen",
//                                icon = R.drawable.courses

                            ),

                            MenuItem(
                                id = "Drinks",
                                title = "Drinks",
                                contentDescription = "get drinks",
//                                icon = R.drawable.drink
                            ),
                            MenuItem(
                                id = "Indian",
                                title = "Indian",
                                contentDescription = "Get indian recipes",
//                                icon = R.drawable.sweets
                            ),
                            MenuItem(
                                id = "cakes",
                                title = "Pastries",
                                contentDescription = "Get help",
//                                icon = R.drawable.sweets
                            ),
                            MenuItem(
                                id = "profile",
                                title = "My Profile",
                                contentDescription = "Get profile",
//                                icon = R.drawable.logout2
                            ),
                            MenuItem(
                                id = "myrecipes",
                                title = "My Recipes",
                                contentDescription = "Get my recipes",
//                                icon = R.drawable.logout2
                            ),
                            MenuItem(
                                id = "logout",
                                title = "Logout",
                                contentDescription = "Get help",
//                                icon = R.drawable.logout2
                            ),
                        ),
                        onItemClick = { menuItem ->

                            scope.launch {
                                when (menuItem.id) {
                                    "categories" -> {
                                        navController.navigate(BottomBarScreen.Categories.route)
//                                        scaffoldState.drawerState.close() // Close the drawer
                                    }

                                    "courses" -> {
                                        navController.navigate("Courses")
//                                        scaffoldState.drawerState.close() // Close the drawer
                                    }
                                    "All Recipes"->{
                                        navController.navigate(BottomBarScreen.Recipes.route)
                                    }
                                    "Drinks" ->{
                                        navController.navigate("Drinks")
                                    }
                                    "Indian" ->{
                                        navController.navigate("Indian")
                                    }
                                    "cakes" ->{
                                        navController.navigate("Cakes")
                                    }
                                    "myrecipes" ->{
                                        navController.navigate("MyRecipes")
                                    }

                                    "logout" ->
                                    {
                                        googleSignInManager!!.signOut(myNavController){
                                            signedIn->if(signedIn){}
                                            else{}
                                        }
                                    }

                                }
                                scaffoldState.drawerState.close() // Close the drawer
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



@Composable
fun getCourses(navController: NavController)  {
    var courses by remember { mutableStateOf(emptyList<Recipe>()) }

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(coroutineScope) {
        val results = coroutineScope.async {
            val breakfast = searchRecipesFromFirebase("Breakfast", SearchType.ByType)
            val lunch = searchRecipesFromFirebase("lunch", SearchType.ByType)
            val dinner = searchRecipesFromFirebase("Dinner", SearchType.ByType)

            listOf(breakfast, lunch, dinner).flatten()
        }.await()

        courses = results
    }
    RecipePage(recipes = courses, navController = navController)
//
}

@Composable
fun getDrinks(navController: NavController)  {
    var drinks by remember { mutableStateOf(emptyList<Recipe>()) }

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(coroutineScope) {
        val results = coroutineScope.async {
//            Tea" , "Coffee" , "Juice" , "Smoothie"
            val breakfast = searchRecipesFromFirebase("Tea", SearchType.ByDrinkType)
            val lunch = searchRecipesFromFirebase("Coffee", SearchType.ByDrinkType)
            val dinner = searchRecipesFromFirebase("Juice", SearchType.ByDrinkType)
            val smoothie = searchRecipesFromFirebase("Smoothie", SearchType.ByDrinkType)

            listOf(breakfast, lunch, dinner, smoothie ).flatten()
        }.await()

        drinks = results
    }
    RecipePage(recipes = drinks, navController = navController)
//
}

@Composable
fun getIndianRecipes(navController: NavController)  {
    var iRecipes by remember { mutableStateOf(emptyList<Recipe>()) }

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(coroutineScope) {
        val results = coroutineScope.async {
//            Tea" , "Coffee" , "Juice" , "Smoothie"
            val recipes = searchRecipesFromFirebase("Indian", SearchType.ByCuisine)
            listOf(recipes).flatten()
        }.await()

        iRecipes = results
    }
    RecipePage(recipes = iRecipes, navController = navController)
//
}
@Composable
fun getCakes(navController: NavController)  {
    var iRecipes by remember { mutableStateOf(emptyList<Recipe>()) }

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(coroutineScope) {
        val results = coroutineScope.async {
//            Tea" , "Coffee" , "Juice" , "Smoothie"
            val recipes = searchRecipesFromFirebase("Pastries", SearchType.ByType)
            listOf(recipes).flatten()
        }.await()

        iRecipes = results
    }
    RecipePage(recipes = iRecipes, navController = navController)
//
}

