package com.example.recipebook

//@Composable
//fun RecipeCard (data : Recipe, navController: NavController) {
//
//    var isLiked by remember { mutableStateOf(false) }
//
//    Card(
//        modifier = Modifier
//            .padding(8.dp)
//            .clickable {
//                navController.navigate("videoPage")
//            },
//        elevation = 4.dp,
//        backgroundColor = Color.White,
//        shape = MaterialTheme.shapes.medium
//    ) {
//
//        Column() {
//            Box {
//                Image(
//                    painter = painterResource(id = data.coverImageUri), // Replace with your actual image resource
//                    contentDescription = null,
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(150.dp)
//                        .clip(shape = MaterialTheme.shapes.medium)
//                        .alpha(0.9f),
//                    contentScale = ContentScale.Crop
//                )
//
//                val icon: ImageVector = if (isLiked) Icons.Default.Favorite else Icons.Default.FavoriteBorder
//
//                Icon(
//                    imageVector = icon,
//                    contentDescription = null,
//                    tint = if (isLiked) Color.Red else Color.Black,
//                    modifier = Modifier
//                        .size(50.dp)
//                        .padding(8.dp)
//                        .align(Alignment.TopEnd)
//                        .clickable {
//                            isLiked = !isLiked
//                        }
//                )
//            }
//            Spacer(modifier = Modifier.height(8.dp))
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(8.dp)
//            ) {
//                data.title?.let {
//                    Text(
//                        text = it,
//                        fontWeight = FontWeight.Light,
//                        fontSize = 14.sp,
//                        color = Color.Black,
//                        overflow = TextOverflow.Ellipsis,
//                        maxLines = 1,
//                        textAlign = TextAlign.Center,
//                        modifier = Modifier.fillMaxWidth(),
//                    )
//                }
//            }
//        }
//
//    }
//}

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.*
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.recipebook.data.Recipe

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun RecipeCard(data: Recipe, navController: NavController,showDesc : Boolean?) {
    var isLiked by remember { mutableStateOf(false) }
    var fav :AddFavorites = AddFavorites()
    fun toggleLikeStatus() {

        isLiked = !isLiked

        if (isLiked) {
            // Add the recipe to favorites
            data.id?.let { fav.addRecipeToFavorites(it) }
        } else {
            // Remove the recipe from favorites
            data.id?.let { fav.removeRecipeFromFavorites(it) }

        }
    }
    Card(
        modifier = Modifier
            .padding(8.dp)
            .clickable {
                navController.navigate("videoPage/${data.id}")
            },
        elevation = 4.dp,
        backgroundColor = Color.White,
        shape = MaterialTheme.shapes.medium
    ) {

        Column() {
            Box {

                GlideImage(
                    model =  data.coverImageUri,
                    contentDescription = data.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .clip(shape = MaterialTheme.shapes.medium)
                        .alpha(0.9f),
                    contentScale = ContentScale.Crop
                )
               {
                    // Glide request options (error and placeholder)
                    it
                        .error(R.drawable.ic_launcher_background)
                        .placeholder(R.drawable.ic_launcher_foreground)
                        .load(data.coverImageUri)
                }


                val icon: ImageVector = if (isLiked) Icons.Default.Favorite else Icons.Default.FavoriteBorder

                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = if (isLiked) Color.Red else Color.Black,
                    modifier = Modifier
                        .size(50.dp)
                        .padding(8.dp)
                        .align(Alignment.TopEnd)
                        .clickable {
                            toggleLikeStatus()
                        }
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                data.title?.let {
                    Text(
                        text = it,
                        fontWeight = FontWeight.Light,
                        fontSize = 16.sp,
                        color = Color.Black,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            }
            if (showDesc == true){
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    data.description?.let {
                        Text(
                            text = it,
                            fontWeight = FontWeight.Light,
                            fontSize = 14.sp,
                            color = Color.Gray,
                            overflow = TextOverflow.Visible,
                            maxLines = 5,
                            textAlign = TextAlign.Left,
                            modifier = Modifier.fillMaxWidth(),
                        )
                    }
                }
            }
        }
    }
}

