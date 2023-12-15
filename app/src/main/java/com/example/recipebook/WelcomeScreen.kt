package com.example.recipebook

import android.net.Uri
import android.view.ViewGroup
import androidx.annotation.OptIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.common.util.Util
import androidx.media3.datasource.DefaultDataSourceFactory
import androidx.media3.exoplayer.SimpleExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
@OptIn(UnstableApi::class)
@Composable
fun WelcomeScreen(onClick: () -> Unit) {
//    val exoPlayer by remember { mutableStateOf<SimpleExoPlayer?>(null) }
//    ExoPlayerSingleton.exoPlayer
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable { onClick() }
            .background(color = Color(0xFFFAF8F2)) // Adjust background color as needed
    ) {
        ExoPlayerSingleton.exoPlayer?.let { player ->
            CustomPlayerView(player, onClick)
        }
    }
}

@OptIn(UnstableApi::class) @Composable
fun CustomPlayerView(player: SimpleExoPlayer, onClick: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        AndroidView(factory = { ctx ->
            PlayerView(ctx).apply {
                useController = false // Hide video controls
                this.player = player
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FILL // Ensure video fills the entire view
                setOnTouchListener { _, _ ->
                    onClick()
                    true
                }
            }
        })
    }
}