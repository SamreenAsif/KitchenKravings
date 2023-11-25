package com.example.recipebook

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
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
import com.example.recipebook.data.ItemCategory


@Composable
fun CategoryButton (
    images: List<ItemCategory> ,
    numColumns: Int? = null,
    maxHeight: Dp? = Dp.Unspecified,
    min : Dp? = 110.dp
){
    Column(
        modifier = Modifier.padding(bottom=30.dp)
        ,

        verticalArrangement = Arrangement.spacedBy(50.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        maxHeight?.let {
                Modifier
                    .fillMaxWidth()
                    .height(it)
            }?.let {
            Box(
                modifier = it // Set the provided maxHeight or the default value
            ) {
                min?.let { it1 -> GridCells.Adaptive(minSize = it1) }?.let { it2 ->
                    LazyVerticalGrid(
                        columns = it2,
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(images) { category ->
                            ElevatedButtonExample(category)
                        }
                    }
                }
            }
        }
    }
}
@Composable
fun ElevatedButtonExample(items: ItemCategory) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        ),
        modifier = Modifier
            .size(width = 150.dp, height = 110.dp),

        colors = CardDefaults.cardColors(
            containerColor = Color(android.graphics.Color.parseColor("#f06d0a")), //Card background color
            contentColor = Color.White  //Card content color,e.g.text
        )
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
//                .border(1.dp , Color.Black)
        ) {

            // Create icon using the img variable passed to it
            Image(
                painter = painterResource(id = items.imgResId),
                contentDescription = null, // provide a content description if needed
                modifier = Modifier
                    .size(50.dp) // Adjust size as needed
                    .padding(8.dp) // Adjust padding as needed
            )

            // Other components can be added here, and they will be arranged vertically
            Text(items.title)
        }
    }
}