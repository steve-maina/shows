package com.example.shows.data

import androidx.work.BackoffPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import com.example.shows.data.work.EpisodesWorker
import java.util.concurrent.TimeUnit

object Constants {
    val CHANNEL_ID = "upcoming_episodes"

    val episodeGroup = "episode group"

}