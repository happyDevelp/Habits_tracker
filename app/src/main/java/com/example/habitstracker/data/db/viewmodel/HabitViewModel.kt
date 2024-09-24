package com.example.habitstracker.data.db.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habitstracker.data.db.HabitEntity
import com.example.habitstracker.data.db.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HabitViewModel(private val repository: Repository) : ViewModel() {

    private val _habitsList = MutableStateFlow<List<HabitEntity>>(emptyList())
    val habitsList: StateFlow<List<HabitEntity>> = _habitsList

    fun addHabit(habit: HabitEntity) {
        viewModelScope.launch {
            repository.addHabit(habit)
        }
    }
    init {
        // Підписка на дані про звички з бази
        viewModelScope.launch {
            repository.getAllHabits().collect { habits ->
                _habitsList.value = habits
            }
        }
    }
    fun deleteHabit(habit: HabitEntity) {
        viewModelScope.launch {
            repository.deleteHabit(habit)
        }
    }

    suspend fun getAllHabits(): Flow<List<HabitEntity>> {
        return repository.getAllHabits()
    }

}
