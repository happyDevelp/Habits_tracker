package com.example.habitstracker.habit.presentation.today_main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habitstracker.habit.domain.HabitEntity
import com.example.habitstracker.habit.domain.HabitRepository
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
    private val _habitsList = MutableStateFlow<List<HabitEntity>>(emptyList())
    val habitsList: StateFlow<List<HabitEntity>> = _habitsList

    init {
        // subscribe on data from database
        viewModelScope.launch {
            getAllHabits().collect { habits ->
                _habitsList.value = habits
            }
        }
    }

    fun addHabit(habit: HabitEntity) {
        viewModelScope.launch {
            habitRepository.addHabit(habit)
        }
    }
    
    fun deleteHabit(habit: HabitEntity) {
        viewModelScope.launch {
            habitRepository.deleteHabit(habit)
        }
    }

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
}