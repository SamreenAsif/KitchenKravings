package com.example.recipebook

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.recipebook.data.ItemCategory

@Composable
fun CategoryButton (
    images: List<ItemCategory> ,
    navController : NavController,
    numColumns: Int? = null,
    maxHeight: Dp? = Dp.Unspecified,
    min : Dp? = 110.dp
){
    Column(
        modifier = Modifier
           ,
        verticalArrangement = Arrangement.spacedBy(50.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        maxHeight?.let {
            Modifier
                .fillMaxWidth()
                .height(it)
        }?.let {
            Box(
                modifier = it // Set the provided maxHeight or the default value
            ) {
                min?.let { minSize ->
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(numColumns ?: 3), // Set the desired number of columns
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(images) { category ->
                            ElevatedButtonExample(category, navController = navController)
                        }
                    }
                }
            }
        }
    }
}




@Composable
fun ElevatedButtonExample(item: ItemCategory , navController: NavController) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
            .size(width = 150.dp, height = 110.dp)
            .clickable {
                navController.navigate("categoryRecipes/${item.title}")
            },

        colors = CardDefaults.cardColors(
            containerColor = Color(android.graphics.Color.parseColor("#f06d0a")),
            contentColor = Color.White
        )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
        ) {
            Image(
                painter = painterResource(id = item.imgResId),
                contentDescription = null,
                modifier = Modifier
                    .size(50.dp)
                    .padding(8.dp)
            )
            Text(item.title)
        }
    }
}

