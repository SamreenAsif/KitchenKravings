package com.example.recipebook

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.recipebook.data.Recipe


@Composable
fun RecipePage(
    recipes: List<Recipe>,
    navController: NavController,
    numColumns: Int? = 2,
    maxHeight: Dp? = Dp.Unspecified,
    bottompadding: Dp = 2.dp
) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(2.dp)
            .padding(bottom= bottompadding),
        color = Color.White,
    ) {
        Column(
            modifier = Modifier
                ,
            verticalArrangement = Arrangement.spacedBy(30.dp),
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
                    LazyVerticalGrid(
                        columns = if (numColumns != null && numColumns > 0) {
                            GridCells.Fixed(numColumns)
                        } else {
                            GridCells.Adaptive(minSize = 160.dp)
                        },
                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(3.dp),
                        horizontalArrangement = Arrangement.spacedBy(0.dp)
                    ) {
                        items(recipes) { recipe ->
                            RecipeCard(data = recipe , navController)
                        }
                    }
                }
            }
        }
    }
}
