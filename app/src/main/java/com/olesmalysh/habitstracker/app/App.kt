package com.olesmalysh.habitstracker.app

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.olesmalysh.habitstracker.core.filling_habits.AppForegroundObserver
import com.olesmalysh.habitstracker.core.notification.data.ReminderBootstrapper
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class App : Application(), Configuration.Provider {

    @Inject lateinit var workerFactory: HiltWorkerFactory
    @Inject lateinit var reminderBootstrapper: ReminderBootstrapper
    @Inject lateinit var appForegroundObserver: AppForegroundObserver


    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

    override fun onCreate() {
        super.onCreate()

        // Register process lifecycle observer (runs on app foreground)
        androidx.lifecycle.ProcessLifecycleOwner.get()
            .lifecycle
            .addObserver(appForegroundObserver)

        // Run bootstrap once (non-blocking)
        CoroutineScope(SupervisorJob() + Dispatchers.Default).launch {
            reminderBootstrapper.runIfNeeded()
        }


    }
}