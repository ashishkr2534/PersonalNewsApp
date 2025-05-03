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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView

/**
 * Created by Ashish Kr on 02,May,2025
 */
//@Composable
//fun VideoSplashScreen(onVideoCompleted: () -> Unit) {
//    val context = LocalContext.current
//    val videoView = remember { VideoView(context) }
//
//    // Create a LaunchedEffect to handle the video completion
//    LaunchedEffect(Unit) {
//        videoView.setVideoURI(Uri.parse("android.resource://${context.packageName}/raw/splash_video"))
//        videoView.setOnCompletionListener {
//            onVideoCompleted()
//        }
//        videoView.start()
//    }
//
//    // Add VideoView to the Compose hierarchy
//    AndroidView(
//        factory = { videoView },
//        modifier = Modifier.fillMaxSize()
//    )
//}

@Composable
fun VideoSplashScreen(onVideoCompleted: () -> Unit) {
    val context = LocalContext.current

    AndroidView(
        factory = {
            VideoView(context).apply {
                val videoUri = Uri.parse("android.resource://${context.packageName}/raw/splash_video")
                setVideoURI(videoUri)
                setOnCompletionListener {
                    onVideoCompleted()
                }

                // Ensure video fills the screen
                layoutParams = FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.MATCH_PARENT
                ).apply {
                    gravity = Gravity.CENTER
                }

                setOnPreparedListener { mediaPlayer ->
                    mediaPlayer.isLooping = false
                    mediaPlayer.setVideoScalingMode(MediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING)
                }

                start()
            }
        },
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxSize()
    )
}

//@Composable
//fun VideoSplashScreen(onVideoCompleted: () -> Unit) {
//    val context = LocalContext.current
//
//    AndroidView(
//        factory = {
//            val videoView = VideoView(context).apply {
//                val uri = Uri.parse("android.resource://${context.packageName}/raw/splash_video")
//                setVideoURI(uri)
//
//                setOnPreparedListener { mp ->
//                    mp.isLooping = false
//
//                    // Calculate aspect ratio adjustment
//                    val videoRatio = mp.videoWidth.toFloat() / mp.videoHeight
//                    val screenRatio = Resources.getSystem().displayMetrics.widthPixels.toFloat() /
//                            Resources.getSystem().displayMetrics.heightPixels
//
//                    if (videoRatio > screenRatio) {
//                        scaleX = videoRatio / screenRatio
//                    } else {
//                        scaleY = screenRatio / videoRatio
//                    }
//                }
//
//                setOnCompletionListener {
//                    onVideoCompleted()
//                }
//
//                start()
//            }
//
//            videoView.layoutParams = FrameLayout.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.MATCH_PARENT
//            )
//
//            videoView
//        },
//        modifier = Modifier
//            .fillMaxSize()
//    )
//}

