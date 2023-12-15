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
import androidx.navigation.NavController
import com.example.recipebook.Lists.Beverages
import com.example.recipebook.Lists.CuisineList
import com.example.recipebook.Lists.Desserts
import com.example.recipebook.Lists.Diet
import com.example.recipebook.Lists.MealTypes
import com.example.recipebook.Lists.Proteins
import com.example.recipebook.Lists.coursesList
import com.example.recipebook.data.ItemCategory



@Composable
fun CategoryScreen(navController: NavController) {
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
                ListItem(items = coursesList , "Courses",navController= navController, null , 130.dp)

            }
            item {
                ListItem(items = CuisineList , "Cuisines",navController= navController,null , 300.dp)

            }
            item {
                ListItem(items = Beverages , "Beverages",navController= navController,null , 250.dp)
            }
            item {
                ListItem(items = MealTypes , "MealTypes ",navController= navController,null , 250.dp)
            }
            item {
                ListItem(items = Proteins , "Proteins",navController= navController,null , 130.dp)
            }
            item {
                ListItem(items = Desserts , "Desserts",navController= navController,null , 250.dp)
            }
            item {
                ListItem(items = Diet , "Dietary Preferences",navController= navController,null , 300.dp)
            }

        }
    }
}

@Composable
fun ListItem(
    items :List<ItemCategory>,
    categoryTitle : String,
    navController: NavController,
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
        CategoryButton(items,navController= navController, numColumns, maxHeight,min)
    }
}