package com.olesmalysh.habitstracker.core.notification

import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import java.time.Duration
import java.time.ZonedDateTime
import java.util.concurrent.TimeUnit

class DailyReminderScheduler(
    private val workManager: WorkManager
) {

    companion object {
        private const val UNIQUE_NAME = "DailyReminder"
    }

    fun schedule(hour: Int, minute: Int) {
        val delayMs = computeDelayToNext(hour, minute)

        val request = OneTimeWorkRequestBuilder<DailyReminderWorker>()
            .setInitialDelay(delayMs, TimeUnit.MILLISECONDS)
            .addTag(UNIQUE_NAME)
            .build()

        workManager.enqueueUniqueWork(
            UNIQUE_NAME,
            ExistingWorkPolicy.REPLACE,
            request
        )
    }

    fun cancel() {
        workManager.cancelUniqueWork(UNIQUE_NAME)
    }

    private fun computeDelayToNext(hour: Int, minute: Int): Long {
        val now = ZonedDateTime.now()
        var next = now
            .withHour(hour)
            .withMinute(minute)
            .withSecond(0)
            .withNano(0)

        if (!next.isAfter(now)) {
            next = next.plusDays(1)
        }

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