package com.olesmalysh.habitstracker.app

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.WorkManager
import com.olesmalysh.habitstracker.core.filling_habits.DailyRolloverScheduler
import com.olesmalysh.habitstracker.core.notification.DailyReminderScheduler
import com.olesmalysh.habitstracker.profile.data.local.AppPreferences
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {
    @Inject
    lateinit var workerFactory: HiltWorkerFactory
    @Inject
    lateinit var appPreferences: AppPreferences


    override fun onCreate() {
        super.onCreate()

        CoroutineScope(SupervisorJob() + Dispatchers.Default).launch {
            val enabled = appPreferences.reminderEnabled.first()
            if (enabled == false) {
                DailyReminderScheduler.cancel(applicationContext)
                return@launch
            }

            val hour = appPreferences.reminderHour.first()
            val minute = appPreferences.reminderMinute.first()

            DailyReminderScheduler.schedule(applicationContext, hour/*19*/, minute/*24*/)
        }

        // Initialize WorkManager with HiltWorkerFactory (manual init)
        val config = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

        WorkManager.initialize(this, config)

        // Schedule nightly DB rollover
        DailyRolloverScheduler.schedule(this)
    }
}