package com.example.habitstracker.history.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habitstracker.habit.domain.DateHabitEntity
import com.example.habitstracker.history.domain.HistoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val historyRepository: HistoryRepository
) : ViewModel() {
    private val _dateHabitsList = MutableStateFlow<List<DateHabitEntity>>(emptyList())
    val dateHabitList: StateFlow<List<DateHabitEntity>> = _dateHabitsList.asStateFlow()

    init {
        viewModelScope.launch {
            getAllDatesForStreak().collect { habits ->
                _dateHabitsList.value = habits
            }
        }
    }

    private suspend fun getAllDatesForStreak(): Flow<List<DateHabitEntity>> {
        return historyRepository.getAllDatesForStreak()
    }
}