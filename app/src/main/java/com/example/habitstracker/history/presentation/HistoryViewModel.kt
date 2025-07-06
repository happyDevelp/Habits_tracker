package com.example.habitstracker.history.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habitstracker.habit.domain.DateHabitEntity
import com.example.habitstracker.history.domain.HistoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val historyRepository: HistoryRepository
): ViewModel() {
    private val _dateHabitsList = MutableStateFlow<List<DateHabitEntity>>(emptyList())
    val dateHabitHist: StateFlow<List<DateHabitEntity>> = _dateHabitsList

    init {
        viewModelScope.launch {
            _dateHabitsList.value = historyRepository.getAllDatesForStreak()
        }
    }
}