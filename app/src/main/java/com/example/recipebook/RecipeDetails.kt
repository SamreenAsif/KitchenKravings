package com.example.recipebook

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VideoPage(navController: NavController) {

    val ingredientsList = listOf(
        "Cooking oil 1tbs",
        "Chicken boneless Small cubes 100g",
        "Adrak Lehsan paste",
        "Tandori Masla 1tbs",
        "Pizza sauce"
    )

    val directionsList = listOf(
        "In a frying pan, add cooking oil, chicken and mix well until it changes color ",
        "Add ginger garlic paste",
        "Bake at a preheated oven at 180C until cheese melts(10 minutes)",
        "Serve hot",
    )
    var isLiked by remember { mutableStateOf(false) }
    var shareClicked by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Scaffold(
        containerColor = Color.White,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color.Black,
                ),
                title = {},
                navigationIcon = {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            modifier = Modifier.clickable {
                                navController.popBackStack()
                            },
                            tint = Color.Black
                        )

                    },
                actions = {
                    // Share icon
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = "Share",
                        modifier = Modifier
                            .padding(end = 16.dp)
                            .clickable {
                                shareClicked = true
                            },
                        tint = Color.Black
                    )

                    // Like (heart) icon
                    Icon(
                        imageVector = if (isLiked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Like",
                        modifier = Modifier
                            .padding(end = 16.dp)
                            .clickable {
                                isLiked = !isLiked
                                // Handle like button click if needed
                            }
                        ,
                        tint = Color.Black
                    )
                }
            )
        },
        content = {
            PageContent(ingredientsList, directionsList)
        },

    )
            // Launch the effect when the share icon is clicked
        LaunchedEffect(shareClicked) {
            if (shareClicked) {
                shareOnWhatsApp(context)
                shareClicked = false // Reset the state
            }
        }

}

@Composable
fun PageContent(ingredientsList:List<String> , directionsList : List<String>){

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Ingredients list
        item {
            Text(
                text = "Italian Double Cheese Pizza",
                modifier = Modifier
                    .fillMaxWidth(),
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        }

        // Video
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(MaterialTheme.shapes.medium)
            ) {
                PlayVideo()
            }
        }

        // Ingredients list
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Italian Double Cheese Pizza",
                modifier = Modifier
                    .fillMaxWidth(),
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        }

        // Coupon for Ingredients
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Coupon(
                title = "Ingredients",
                points = ingredientsList
            )
        }

        // Coupon for Directions
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Coupon(
                title = "Directions",
                points = directionsList
            )
        }

        // Add to Grocery List button
        item {
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = {
                    // Handle "Add to Grocery List" button click
                },
                modifier = Modifier
                   .width(190.dp)
            ) {
                Text("Add to Grocery List")
            }
        }
    }

}
fun shareOnWhatsApp(context: Context) {
    val textToShare = "Check out this amazing recipe!"

    val sendIntent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, textToShare)
        type = "text/plain"
    }

    sendIntent.setPackage("com.whatsapp")

    if (sendIntent.resolveActivity(context.packageManager) != null) {
        context.startActivity(sendIntent)
    } else {
        Toast.makeText(context, "WhatsApp is not installed", Toast.LENGTH_SHORT).show()
    }
}

@Composable
fun Coupon(title: String, points: List<String>) {
    Column(
        modifier = Modifier
            .border(
                border = BorderStroke(5.dp, Color.LightGray),
                shape = MaterialTheme.shapes.medium
            )
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Text(text = title, color = Color.Black)

        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            points.forEach { point ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "•", color = Color.Black)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = point, color = Color.Black)
                }
            }
        }
    }
}


@Composable
fun YouTubePlayer(videoId: String) {
    val url = "https://www.youtube.com/embed/$videoId"

    AndroidView(factory = { context ->
        WebView(context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true
            loadUrl(url)
        }
    })
}

@Composable
fun PlayVideo() {
    val youtubeVideoId = "D0TD-7NBSyI"
    YouTubePlayer(videoId = youtubeVideoId)
}