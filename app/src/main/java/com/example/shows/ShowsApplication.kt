package com.example.shows

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.os.Build
import androidx.core.content.ContextCompat.getSystemService
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.BackoffPolicy
import androidx.work.Configuration
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ListenableWorker
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.example.shows.data.Constants
import com.example.shows.data.Constants.CHANNEL_ID
import com.example.shows.data.local.ShowsDatabase
import com.example.shows.data.work.EpisodesWorker
import dagger.hilt.android.HiltAndroidApp
import io.ktor.client.HttpClient
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltAndroidApp
class ShowsApplication: Application(), Configuration.Provider {
    @Inject
    lateinit var workerFactory: CustomWorkerFactory
    override val workManagerConfiguration: Configuration
        get() =  Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()



    override fun onCreate() {
        super.onCreate()
        createNotificationChannel(this)
//        val workRequestMine = PeriodicWorkRequestBuilder<EpisodesWorker>(
//            1, TimeUnit.HOURS
//        )
//            .setBackoffCriteria(BackoffPolicy.LINEAR,10, TimeUnit.MINUTES)
//            .build()
//        WorkManager.getInstance(this)
//            .enqueueUniquePeriodicWork(
//                "get episodes",
//                ExistingPeriodicWorkPolicy.REPLACE,
//                workRequestMine
//            )
    }
}
class CustomWorkerFactory @Inject constructor(
    private val db : ShowsDatabase,
    private val ktorclient: HttpClient
): WorkerFactory(){
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker = EpisodesWorker(showsDatabase = db,
        ktorclient = ktorclient,
        appContext =  appContext,
     workerParams = workerParameters)

}

private fun createNotificationChannel(context: Context) {
    // Create the NotificationChannel, but only on API 26+ because
    // the NotificationChannel class is not in the Support Library.
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val name = "Upcoming Episodes"
        val descriptionText = name
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
            description = descriptionText
        }
        // Register the channel with the system.
        val notificationManager: NotificationManager =
            context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}
