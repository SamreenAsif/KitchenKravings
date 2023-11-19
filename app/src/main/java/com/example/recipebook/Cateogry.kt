package com.example.recipebook

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.unit.dp


@Composable
fun CategoryButton (images: List<Int> ){
    Column(
        modifier = Modifier
            .padding(5.dp)
//            .fillMaxWidth()
//            .height(200.dp)
            .border(1.dp, Color.Red)
        ,

        verticalArrangement = Arrangement.spacedBy(50.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ){

        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 100.dp) ,
            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(images) {category ->
                ElevatedButtonExample(category)
            }
        }

    }
}
@Composable
fun ElevatedButtonExample(img: Int) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 12.dp
        ),
        modifier = Modifier
            .size(width = 180.dp, height = 90.dp),

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
                painter = painterResource(id = img),
                contentDescription = null, // provide a content description if needed
                modifier = Modifier
                    .size(50.dp) // Adjust size as needed
                    .padding(8.dp) // Adjust padding as needed
            )

            // Other components can be added here, and they will be arranged vertically
            Text("Elevated")
        }
    }
}