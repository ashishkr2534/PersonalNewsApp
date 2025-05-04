package com.ashish.personalnewsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.ashish.personalnewsapp.Navigation.NavGraph
import com.ashish.personalnewsapp.WorkManager.NewsWorker
import com.ashish.personalnewsapp.WorkManager.scheduleNewsWorker
import com.ashish.personalnewsapp.WorkManager.showNotification
import com.ashish.personalnewsapp.ui.theme.PersonalNewsAppTheme
import dagger.hilt.android.AndroidEntryPoint
//import kotlin.coroutines.jvm.internal.CompletedContinuation.context

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        //scheduleNewsWorker(applicationContext)

        setContent {
            PersonalNewsAppTheme {
                val navController = rememberNavController()
                val context = LocalContext.current
                LaunchedEffect(Unit) {
                    //scheduleNewsWorker(applicationContext)
                    scheduleNewsWorker(applicationContext)
                    val request = OneTimeWorkRequestBuilder<NewsWorker>().build()
                    WorkManager.getInstance(context).enqueue(request)
                    showNotification(applicationContext, "News Updated", "Top headlines just refreshed.")

                }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    NavGraph(navController)

                }

            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "HelloJi  $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PersonalNewsAppTheme {
        Greeting("Android")
    }
}

//fun scheduleNewsWorker(context: Context) {
//    val constraints = Constraints.Builder()
//        .setRequiredNetworkType(NetworkType.CONNECTED)
//        .build()
//
//    val request = PeriodicWorkRequestBuilder<NewsWorker>(1, TimeUnit.MINUTES)
//        .setConstraints(constraints)
//        .build()
//
//    WorkManager.getInstance(context).enqueueUniquePeriodicWork(
//        "news_fetch_worker",
//        ExistingPeriodicWorkPolicy.KEEP,
//        request
//    )
//}
