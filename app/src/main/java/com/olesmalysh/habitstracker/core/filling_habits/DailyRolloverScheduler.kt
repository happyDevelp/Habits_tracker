package com.olesmalysh.habitstracker.core.filling_habits

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

object DailyRolloverScheduler {
    private const val UNIQUE_NAME = "DailyRollover"

    fun schedule(context: Context) {
        val request = PeriodicWorkRequestBuilder<DailyRolloverWorker>(
            1, TimeUnit.DAYS
        )
            .addTag("daily_rollover")
            .build()

        WorkManager.getInstance(context)
            .enqueueUniquePeriodicWork(
                UNIQUE_NAME,
                ExistingPeriodicWorkPolicy.UPDATE,
                request
            )
    }

    fun cancel(context: Context) {
        WorkManager.getInstance(context).cancelUniqueWork(UNIQUE_NAME)
    }
}

/*
object DailyRolloverScheduler {
    private const val UNIQUE_NAME = "DailyRollover"

    fun schedule(context: Context) {
        val delayMs = computeDelayToNext(hour = 20, minute = 10)

        //debug: run in 1 min
        //val delayMs = TimeUnit.MINUTES.toMillis(1)

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
}*/
