package com.example.recipebook

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.recipebook.data.ItemCategory


val categoryItems = listOf(
    ItemCategory(title = "Breakfast", imgResId = R.drawable.englishbreakfast),
    ItemCategory(title = "Lunch", imgResId = R.drawable.lunch),
    ItemCategory(title = "Dinner", imgResId = R.drawable.christmasdinner),
    ItemCategory(title = "Desserts", imgResId = R.drawable.cupcake),
    ItemCategory(title = "Drinks", imgResId = R.drawable.drink),
    ItemCategory(title = "Breakfast", imgResId = R.drawable.englishbreakfast),
    ItemCategory(title = "Lunch", imgResId = R.drawable.lunch),
    ItemCategory(title = "Dinner", imgResId = R.drawable.christmasdinner),
    ItemCategory(title = "Desserts", imgResId = R.drawable.cupcake),

    // Add more items as needed
)
val coursesList = listOf(
    ItemCategory(title = "Breakfast", imgResId = R.drawable.englishbreakfast),
    ItemCategory(title = "Lunch", imgResId = R.drawable.lunch),
    ItemCategory(title = "Dinner", imgResId = R.drawable.christmasdinner),

    // Add more items as needed
)
val CuisineList = listOf(
    ItemCategory(title = "Breakfast", imgResId = R.drawable.englishbreakfast),
    ItemCategory(title = "Lunch", imgResId = R.drawable.lunch),
    ItemCategory(title = "Dinner", imgResId = R.drawable.christmasdinner),
    ItemCategory(title = "Desserts", imgResId = R.drawable.cupcake),
    ItemCategory(title = "Drinks", imgResId = R.drawable.drink),
    ItemCategory(title = "Breakfast", imgResId = R.drawable.englishbreakfast),
    ItemCategory(title = "Lunch", imgResId = R.drawable.lunch),
    ItemCategory(title = "Dinner", imgResId = R.drawable.christmasdinner),
    ItemCategory(title = "Desserts", imgResId = R.drawable.cupcake),

    // Add more items as needed
)
@Composable
fun CategoryScreen() {
    Surface(
        modifier = Modifier
            .padding(bottom = 30.dp)
            .fillMaxWidth()
            .fillMaxHeight(),
    ) {
        LazyColumn(
            contentPadding = PaddingValues(start=8.dp ,end= 8.dp ,top=30.dp ,bottom = 50.dp)
        )
        {
            item {
                ListItem(items = coursesList , "Courses",null , 130.dp)

            }
            item {
                ListItem(items = CuisineList , "Cuisines",null , 380.dp)

            }
            item {
                ListItem(items = CuisineList , "Cuisines",null , 380.dp)

            }
        }
    }
}

@Composable
fun ListItem(
    items :List<ItemCategory>,
    categoryTitle : String,
    numColumns: Int? = null,
    maxHeight: Dp? = Dp.Unspecified,
    min : Dp? = 110.dp)
{
    Column() {
        Text(
            text = categoryTitle,
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black,
            modifier = Modifier.padding(
                start = 20.dp,
                top = 8.dp,
                bottom = 20.dp
            )
        )
        CategoryButton(items, numColumns, maxHeight,min)
    }
}