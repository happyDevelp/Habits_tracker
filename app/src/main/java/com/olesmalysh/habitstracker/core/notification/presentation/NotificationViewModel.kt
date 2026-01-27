package com.olesmalysh.habitstracker.core.notification.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.olesmalysh.habitstracker.core.notification.DailyReminderScheduler
import com.olesmalysh.habitstracker.core.notification.domain.ReminderSettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class NotificationUiState(
    val enabled: Boolean = true,
    val hour: Int = 20,
    val minute: Int = 0,
    val timeText: String = "20:00"
)

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val repository: ReminderSettingsRepository,
    private val scheduler: DailyReminderScheduler
) : ViewModel() {

    val state: StateFlow<NotificationUiState> =
        repository.settings.map { settings ->
            NotificationUiState(
                enabled = settings.enabled,
                hour = settings.hour,
                minute = settings.minute,
                timeText = settings.timeText
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = NotificationUiState()
        )

    fun onToggle(enabled: Boolean) {
        viewModelScope.launch {
            repository.setEnabled(enabled)

            val current = repository.settings.first()

            if (enabled) {
                scheduler.schedule(current.hour, current.minute)
            } else {
                scheduler.cancel()
            }
        }
    }

    fun onTimeSelected(hour: Int, minute: Int) {
        viewModelScope.launch {
            repository.setTime(hour, minute)

            val current = repository.settings.first()
            if (current.enabled) {
                scheduler.schedule(hour, minute)
            }
        }
    }
}