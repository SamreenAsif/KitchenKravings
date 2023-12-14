package com.example.recipebook

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ShoppingListScreen(navController: NavController) {
    var shoppingList by remember { mutableStateOf(emptyList<String>()) }
    val shplist = ShoppingList()

    LaunchedEffect(Unit) {
        // Fetch the shopping list from the database
        shoppingList = shplist.getShoppingList()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
            .background(Color.LightGray)

    ) {
        TopAppBar(
            title = { Text(text = "Shopping List") },
            navigationIcon = {
                IconButton(
                    onClick = { /* Handle navigation icon click if needed */ }
                ) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                }
            }
        )

        if (shoppingList.isEmpty()) {
            // Display a message if the shopping list is empty
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Your shopping list is empty.")
            }
        } else {
            // Display the shopping list
            Log.d("Firebase", "Ingredient removed successfully: ")
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {

                items(shoppingList) { item ->
                    ShoppingListItem(

                        item = item,
                        onRemoveItem = { removedItem ->
                            Log.d("fjvfjv" , "$removedItem")
                            shoppingList = shoppingList.toMutableList().also { it.remove(removedItem) }
                            shplist.removeIngredientFromShoppingList(removedItem)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun ShoppingListItem(item: String, onRemoveItem: (String) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
    ) {
        Column(
            modifier = Modifier
                .clickable { onRemoveItem(item) }
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Ingredient name on the left
                Text(
                    text = item,
                    fontSize = 20.sp,
                    color = Color.White
                )

                // "-" sign on the right
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = "Remove",
                    tint = Color.White // Set the tint color to white
                )
            }
        }
    }
}
