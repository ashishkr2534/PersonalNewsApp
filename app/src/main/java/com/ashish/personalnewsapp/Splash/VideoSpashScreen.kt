package com.ashish.personalnewsapp.Splash

import android.content.Context
import android.content.res.Resources
import android.media.MediaPlayer
import android.net.Uri
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.VideoView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
            FrameLayout(context).apply {
                val videoView = FullScreenVideoView(context).apply {
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
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        Gravity.CENTER
                    )
                }

                addView(videoView)
            }
        },
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    )
}


class FullScreenVideoView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : VideoView(context, attrs) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)
        setMeasuredDimension(width, height)
    }
}
