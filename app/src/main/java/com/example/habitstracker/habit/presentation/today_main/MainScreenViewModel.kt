package com.example.habitstracker.habit.presentation.today_main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habitstracker.habit.domain.HabitEntity
import com.example.habitstracker.habit.domain.HabitRepository
import com.example.habitstracker.habit.domain.DateHabitEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val habitRepository: HabitRepository
) : ViewModel() {
    private val _habitsListState = MutableStateFlow<List<HabitEntity>>(emptyList())
    val habitsListState: StateFlow<List<HabitEntity>> = _habitsListState

    init {
        // subscribe on data from database
        viewModelScope.launch {
            getAllHabits().collect { habits ->
                _habitsListState.value = habits
            }
        }
    }

    fun addHabit(habit: HabitEntity, onHabitAdded: (habitId: Long) -> Unit) {
        viewModelScope.launch {
            val id = habitRepository.addHabit(habit)
            onHabitAdded(id)
        }
    }

    fun deleteHabit(id: Int) {
        viewModelScope.launch {
            habitRepository.deleteHabit(id)
        }
    }

    /*fun deleteHabitAndDate(id: Int) {
        viewModelScope.launch {
            habitRepository.deleteHabitAndDate(id)
        }
    }*/

    suspend fun updateHabit(habit: HabitEntity) {
        return habitRepository.updateHabit(habit)
    }

    fun updateSelectedState(id: Int, isDone: Boolean) {
        viewModelScope.launch {
            habitRepository.updateSelectedState(id, isDone)
        }
    }

    suspend fun getAllHabits(): Flow<List<HabitEntity>> {
        return habitRepository.getAllHabits()
    }

    fun insertHabitDate(status: DateHabitEntity) {
        viewModelScope.launch {
            habitRepository.insertHabitDate(status)
        }
    }

    fun updateHabitAndDateSelectState(id: Int, isDone: Boolean) {
        viewModelScope.launch {
            habitRepository.updateHabitAndDateSelectState(id, isDone)
        }
    }

    fun setHabitsForDate(date: String /*YYYY-MM-DD*/) {
        viewModelScope.launch {
            habitRepository.getHabitsByDate(date).collect {habits ->
                _habitsListState.value = habits
            }
        }
    }
}