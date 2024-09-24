package com.example.habitstracker.data.db.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habitstracker.data.db.HabitEntity
import com.example.habitstracker.data.db.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HabitViewModel(private val repository: Repository) : ViewModel() {

   // val _habitsList: MutableStateFlow<List<HabitEntity>> = getAllHabits()

    fun addHabit(habit: HabitEntity) {
        viewModelScope.launch {
            repository.addHabit(habit)
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
