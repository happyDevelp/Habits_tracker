package com.olesmalysh.habitstracker.core.notification

import android.content.Context
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import java.time.Duration
import java.time.ZonedDateTime
import java.util.concurrent.TimeUnit

object DailyReminderScheduler {
    private const val UNIQUE_NAME = "DailyReminder"

    fun schedule(context: Context, hour: Int, minute: Int) {
        val delayMs = computeDelayToNext(hour, minute)

        val request = OneTimeWorkRequestBuilder<DailyReminderWorker>()
            .setInitialDelay(delayMs, TimeUnit.MILLISECONDS)
            .addTag("daily_reminder")
            .build()

        WorkManager.getInstance(context)
            .enqueueUniqueWork(
                UNIQUE_NAME,
                ExistingWorkPolicy.REPLACE,
                request
            )
    }

    fun cancel(context: Context) {
        WorkManager.getInstance(context)
            .cancelUniqueWork(UNIQUE_NAME)
    }

    private fun computeDelayToNext(hour: Int, minute: Int): Long {
        // Compute delay until the next local time occurrence (hour:minute)
        val now = ZonedDateTime.now()
        var next = now.withHour(hour).withMinute(minute).withSecond(0).withNano(0)
        if (!next.isAfter(now)) next = next.plusDays(1)
        return Duration.between(now, next).toMillis()
    }

    // test function
    /*fun scheduleInOneMinute(context: Context) {
        val request = OneTimeWorkRequestBuilder<DailyReminderWorker>()
            .setInitialDelay(1, TimeUnit.MINUTES)
            .addTag("daily_reminder_test")
            .build()

        WorkManager.getInstance(context)
            .enqueueUniqueWork("DailyReminderTest", ExistingWorkPolicy.REPLACE, request)

        Log.d("DailyReminderScheduler", "Scheduled TEST reminder in 1 minute")
    }*/
}