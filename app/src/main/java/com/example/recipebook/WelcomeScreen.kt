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
import androidx.media3.common.util.UnstableApi
import androidx.media3.common.util.Util
import androidx.media3.datasource.DefaultDataSourceFactory
import androidx.media3.exoplayer.SimpleExoPlayer
import androidx.media3.ui.PlayerView

// Import necessary packages

// Import necessary packages

@OptIn(UnstableApi::class)
@Composable
fun WelcomeScreen(onClick: () -> Unit) {
    val context = LocalContext.current
    val videoUrl = "https://v1.pinimg.com/videos/mc/expMp4/5a/3c/3e/5a3c3ee2efe966f46b917e61917ca7ba_t3.mp4"

    var exoPlayer by remember { mutableStateOf<SimpleExoPlayer?>(null) }

    DisposableEffect(context) {
        val player = SimpleExoPlayer.Builder(context).build().apply {
            val dataSourceFactory = DefaultDataSourceFactory(
                context,
                Util.getUserAgent(context, context.packageName)
            )

            val mediaItem = MediaItem.Builder()
                .setUri(Uri.parse(videoUrl))
                .setMimeType(Util.inferContentType(videoUrl).toString())
                .build()

            setMediaItem(mediaItem)
            playWhenReady = true // Start playing automatically
            repeatMode = SimpleExoPlayer.REPEAT_MODE_OFF // Optional: Set repeat mode
            prepare()
        }

        exoPlayer = player

        onDispose {
            player.release()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable { onClick() }
            .background(color = Color(0xFFFAF8F2)) // Adjust background color as needed
    ) {
        exoPlayer?.let { player ->
            CustomPlayerView(player, onClick)
        }
    }
}

@Composable
fun CustomPlayerView(player: SimpleExoPlayer, onClick: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
//        LogoImage()
        AndroidView(factory = { ctx ->
            PlayerView(ctx).apply {
                useController = false // Hide video controls
                this.player = player
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                setOnTouchListener { _, _ ->
                    onClick()
                    true
                }
            }
        })
    }
}
@Composable
fun LogoImage() {
    val context = LocalContext.current

    // Replace R.drawable.your_logo with the actual resource ID of your JPG image
    val logoImage = painterResource(R.drawable.logo)

    Image(
        painter = logoImage,
        contentDescription = null,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, bottom = 16.dp)
            .wrapContentSize(align = Alignment.Center)
    )
}