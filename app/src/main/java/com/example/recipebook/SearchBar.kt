package com.example.recipebook

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.material3.SearchBar
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@ExperimentalMaterial3Api
@Composable
fun SearchBarM3(){
    var query by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }
    // Customize the colors using SearchBarDefaults.colors
    val searchBarColors = SearchBarDefaults.colors(
        containerColor = Color.White,
        dividerColor = Color.Transparent,

    )
    val borderModifier = Modifier
        .border(2.dp, Color.LightGray, RoundedCornerShape(8.dp))
    val paddingModifier = Modifier.padding(
            start = 16.dp, // Left padding
//            top = 8.dp, // Top padding
//            bottom = 8.dp // Bottom padding
        )

    val iconSize = 24.dp

    Box(
        modifier = borderModifier.then(paddingModifier),
        contentAlignment = Alignment.CenterStart
    ) {

        SearchBar(
            query = query,
            onQueryChange = { query = it },
            onSearch = { newQuery ->
                println("Performing search on query: $newQuery")
            },
            active = active,
            onActiveChange = { active = it },
            placeholder = {
                Text(
                    text = if (query.isNotEmpty()) query else "Search",
                    style = TextStyle(
                        color = if (active) Color.Black else Color.Gray, // Text color based on active state
                        fontSize = 18.sp, // Set the font size here
                    )
                )
            },
            leadingIcon = {
                if (!active) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        modifier = Modifier.size(iconSize),
                        tint = Color.Gray
                    )
                }
            },
            trailingIcon = {
                if (active) {
                    IconButton(
                        onClick = { if (query.isNotEmpty()) query = "" else active = false }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            modifier = Modifier.size(iconSize) // Set the icon size here
                        )
                    }
                }
            },
            colors = searchBarColors,

            ) {
        }
    }
}