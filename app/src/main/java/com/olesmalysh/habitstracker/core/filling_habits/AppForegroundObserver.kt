package com.olesmalysh.habitstracker.core.filling_habits

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class AppForegroundObserver @Inject constructor(
    private val fillMissingDatesUseCase: FillMissingDatesUseCase
) : DefaultLifecycleObserver {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private val mutex = kotlinx.coroutines.sync.Mutex()

    override fun onStart(owner: LifecycleOwner) {
        scope.launch {
            // Prevent parallel runs
            mutex.lock()
            try {
                fillMissingDatesUseCase()
            } finally {
                mutex.unlock()
            }
        }
    }
}