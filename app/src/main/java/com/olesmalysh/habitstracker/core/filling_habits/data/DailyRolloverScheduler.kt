package com.olesmalysh.habitstracker.core.filling_habits.data

import android.content.Context
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import java.time.Duration
import java.time.ZonedDateTime
import java.util.concurrent.TimeUnit

object DailyRolloverScheduler {
    private const val UNIQUE_NAME = "DailyRollover"

    fun schedule(context: Context) {
        //val delayMs = computeDelayToNext(hour = 0, minute = 5)

        //debug: run in 1 min
        val delayMs = TimeUnit.MINUTES.toMillis(1)

        val request = OneTimeWorkRequestBuilder<DailyRolloverWorker>()
            .setInitialDelay(delayMs, TimeUnit.MILLISECONDS)
            .addTag("daily_rollover")
            .build()

        WorkManager.getInstance(context)
            .enqueueUniqueWork(UNIQUE_NAME, ExistingWorkPolicy.REPLACE, request)

    }

    private fun computeDelayToNext(hour: Int, minute: Int): Long {
        // Compute delay until the next local time occurrence (hour:minute)
        val now = ZonedDateTime.now()
        var next = now.withHour(hour).withMinute(minute).withSecond(0).withNano(0)
        if (!next.isAfter(now)) next = next.plusDays(1)
        return Duration.between(now, next).toMillis()
    }
}

