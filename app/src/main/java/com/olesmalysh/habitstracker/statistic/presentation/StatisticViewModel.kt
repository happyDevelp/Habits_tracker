package com.olesmalysh.habitstracker.statistic.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.olesmalysh.habitstracker.habit.domain.DateHabitEntity
import com.olesmalysh.habitstracker.statistic.domain.StatisticRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StatisticViewModel @Inject constructor(
    private val repository: StatisticRepository
) : ViewModel() {
    private val _dateHabitsList = MutableStateFlow<List<DateHabitEntity>>(emptyList())

    val dateHabitList: StateFlow<List<DateHabitEntity>> = _dateHabitsList.asStateFlow()

    init {
        viewModelScope.launch {
            getDateHabitList().collect { list ->
                _dateHabitsList.value = list
            }
        }
    }

    private fun getDateHabitList(): Flow<List<DateHabitEntity>> {
        return repository.getDateHabitList()
    }
}