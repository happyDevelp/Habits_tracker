package com.olesmalysh.habitstracker.core.notification.data

import com.olesmalysh.habitstracker.core.notification.DailyReminderScheduler
import com.olesmalysh.habitstracker.core.notification.domain.ReminderSettingsRepository
import kotlinx.coroutines.flow.first

class ReminderBootstrapper(
    private val repository: ReminderSettingsRepository,
    private val scheduler: DailyReminderScheduler
) {
    suspend fun runIfNeeded() {
        // Prevent running bootstrap multiple times
        val alreadyBootstrapped = repository.isBootstrapped()
        if (alreadyBootstrapped) return

        val settings = repository.settings.first()

        // Schedule only if enabled by default / user setting
        if (settings.enabled) {
            scheduler.schedule(settings.hour, settings.minute)
        } else {
            scheduler.cancel()
        }

        // Mark bootstrap completed
        repository.setBootstrapped(true)
    }
}