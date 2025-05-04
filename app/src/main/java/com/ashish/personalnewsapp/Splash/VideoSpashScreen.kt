package com.ashish.personalnewsapp.Splash

import android.content.res.Resources
import android.media.MediaPlayer
import android.net.Uri
import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.VideoView
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView

/**
 * Created by Ashish Kr on 02,May,2025
 */

@Composable
fun VideoSplashScreen(onVideoCompleted: () -> Unit) {
    val context = LocalContext.current

    AndroidView(
        factory = {
            object : FrameLayout(context) {
                override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
                    val displayMetrics = Resources.getSystem().displayMetrics
                    val width = displayMetrics.widthPixels
                    val height = displayMetrics.heightPixels
                    setMeasuredDimension(width, height)
                    super.onMeasure(widthMeasureSpec, heightMeasureSpec)
                }
            }.apply {
                val videoView = VideoView(context).apply {
                    val videoUri = Uri.parse("android.resource://${context.packageName}/raw/adpp_is")
                    setVideoURI(videoUri)

                    setOnPreparedListener { mediaPlayer ->
                        mediaPlayer.isLooping = false
                        mediaPlayer.setVideoScalingMode(
                            MediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING
                        )
                        start()
                    }

                    setOnCompletionListener {
                        onVideoCompleted()
                    }

                    layoutParams = FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.MATCH_PARENT,
                        FrameLayout.LayoutParams.MATCH_PARENT,
                        Gravity.CENTER
                    )
                }

                addView(videoView)
            }
        },
        modifier = Modifier.fillMaxSize()
    )
}


