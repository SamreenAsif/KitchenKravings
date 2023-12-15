package com.example.recipebook

import android.app.Application
import android.net.Uri
import androidx.annotation.OptIn
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import androidx.media3.common.util.Util
import androidx.media3.datasource.DefaultDataSourceFactory
import androidx.media3.exoplayer.SimpleExoPlayer
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ApplicationFirebaseAuth : Application() {
    override fun onCreate() {
        super.onCreate()

        // Load the video in the background during app initialization
        loadVideoInBackground()
    }
    @OptIn(UnstableApi::class)
    private fun loadVideoInBackground() {
        Log.d("TAG", "loading video in background")
        val videoUrl = "https://v1.pinimg.com/videos/mc/720p/a3/6d/40/a36d4000ccd222c63a3339511f3b988a.mp4"

        // Create a global variable to hold the player instance
        val exoPlayer = SimpleExoPlayer.Builder(this).build().apply {
            val dataSourceFactory = DefaultDataSourceFactory(
                this@ApplicationFirebaseAuth,
                Util.getUserAgent(this@ApplicationFirebaseAuth, packageName)
            )

            val mediaItem = MediaItem.Builder()
                .setUri(Uri.parse(videoUrl))
                .setMimeType(Util.inferContentType(videoUrl).toString())
                .build()

            setMediaItem(mediaItem)
            playWhenReady = true
            repeatMode = SimpleExoPlayer.REPEAT_MODE_OFF
            addListener(object : Player.Listener {
                override fun onIsLoadingChanged(isLoading: Boolean) {
                    if (!isLoading) {
                        // Video is loaded, you can perform any necessary actions here
                        // For example, store the player instance in a singleton for later use
                        // or notify any observers that the video is ready
                    }
                }
            })
            prepare()
        }

        ExoPlayerSingleton.exoPlayer = exoPlayer
    }


}

object ExoPlayerSingleton {
    var exoPlayer: SimpleExoPlayer? = null
}
