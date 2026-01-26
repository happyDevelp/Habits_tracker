package com.olesmalysh.habitstracker.app

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.WorkManager
import com.olesmalysh.habitstracker.core.filling_habits.data.DailyRolloverScheduler
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {

    @Inject lateinit var workerFactory: HiltWorkerFactory

    override fun onCreate() {
        super.onCreate()

        // Initialize WorkManager with HiltWorkerFactory (manual init)
        val config = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

        WorkManager.initialize(this, config)

        // Schedule nightly DB rollover
        DailyRolloverScheduler.schedule(this)
    }
}