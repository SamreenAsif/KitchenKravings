package com.example.recipebook

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun HomeScreen() {
    searchBox()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun searchBox(){
    Box(
        modifier = Modifier.height(100.dp),
        contentAlignment = Alignment.TopCenter
    ){
        SearchBarM3()
    }
}

@Composable
@Preview
fun HomeScreenPreview() {
    HomeScreen()
}