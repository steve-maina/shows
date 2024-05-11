package com.example.shows.data.work

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.shows.R
import com.example.shows.data.Constants.CHANNEL_ID
import com.example.shows.data.Constants.episodeGroup
import com.example.shows.data.local.Show
import com.example.shows.data.local.ShowsDatabase
import com.example.shows.data.network.response.Episode
import com.example.shows.data.network.response.SearchShow
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.path
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext
import okhttp3.internal.notify
import java.time.LocalDate
import java.util.Calendar
import java.util.Random

const val TAG = "WorkManager"
@HiltWorker
class EpisodesWorker @AssistedInject constructor(
    @Assisted val appContext: Context,
    @Assisted workerParams: WorkerParameters,
    val showsDatabase: ShowsDatabase,
    val ktorclient: HttpClient
): CoroutineWorker(appContext, workerParams) {
    override suspend fun doWork(): Result {
        return withContext(Dispatchers.IO) {
            try {
                val shows = showsDatabase.getDao().getListShows()
                Log.d(TAG, "${shows}")
                // delay(30000L)
                for (i in 1..100) {
                    showsDatabase.getDao().saveShow(Show("${Calendar.getInstance().time}", i))
                    delay(1000L)
                }
                return@withContext Result.success()
            } catch (e: Exception) {
                return@withContext Result.failure()

            }
        }
    }

//            shows.forEach { show ->
//                    val showResponse: SearchShow = ktorclient.get {
//                        url {
//                            path("shows/${show.id}")
//                        }
//                    }.body()
//                    val title = showResponse.name
//                    val nextEpisodeDate = showResponse.links?.nextEpisode?.href
//                if (nextEpisodeDate == null || title == null || nextEpisodeDate == "" || title == "") {
//                        delay(20000L)
//                        return@forEach
//                    }
//                    val backSlashIndex = nextEpisodeDate.lastIndexOf("/")
//                    val episodeId = nextEpisodeDate.substring(backSlashIndex + 1)
//                Log.d(TAG,episodeId)
//                    val episode: Episode = ktorclient.get {
//                        url {
//                            path("episodes/${episodeId}")
//                        }
//                    }.body()
//                    if (episode.airdate == null || episode.airdate == "") {
//                        delay(20000L)
//                        return@forEach
//                    }
//
//                    displayNotifications(title, episode.airdate)
//                    delay(20000L)
//
//                }
//            //groupNotification()
//            return Result.success()
//        } catch(e:Exception){
//            Log.d("WorkManager",e.message ?: "An error occurred")
//            return Result.retry()
//        }
//    }
//
//
//    fun displayNotifications(title:String,content:String) {
//        val builder = NotificationCompat.Builder(appContext, CHANNEL_ID)
//            .setSmallIcon(R.drawable.movie_filter)
//            .setContentTitle(title)
//            .setContentText(content)
//            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//            .setGroup(episodeGroup)
//        if (ActivityCompat.checkSelfPermission(
//                appContext,
//                Manifest.permission.POST_NOTIFICATIONS
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return
//        }
//        NotificationManagerCompat.from(appContext).notify(
//            Random().nextInt(),
//            builder.build()
//        )
//    }
//    fun groupNotification() {
//        val summaryNotification = NotificationCompat.Builder(appContext, CHANNEL_ID)
//            .setContentTitle("Upcoming episodes")
//            // Set content text to support devices running API level < 24.
//            .setContentText("new upcoming episodes")
//            .setSmallIcon(R.drawable.movie_filter)
//            // Build summary info into InboxStyle template.
//            .setStyle(NotificationCompat.InboxStyle()
//                .setSummaryText("upcoming episodes"))
//            // Specify which group this notification belongs to.
//            .setGroup(episodeGroup)
//            // Set this notification as the summary for the group.
//            .setGroupSummary(true)
//            .build()
//        if (ActivityCompat.checkSelfPermission(
//                appContext,
//                Manifest.permission.POST_NOTIFICATIONS
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return
//        }
//        NotificationManagerCompat.from(appContext).notify(
//            0,
//            summaryNotification
//        )
//    }
}


fun showNotification() {
    
}

