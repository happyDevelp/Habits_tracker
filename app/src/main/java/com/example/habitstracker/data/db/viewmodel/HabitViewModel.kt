package com.example.habitstracker.data.db.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habitstracker.data.db.HabitEntity
import com.example.habitstracker.data.db.repository.DBRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HabitViewModel @Inject constructor(val DBRepository: DBRepository) : ViewModel() {

    private val _habitsList = MutableStateFlow<List<HabitEntity>>(emptyList())
    val habitsList: StateFlow<List<HabitEntity>> = _habitsList

    fun addHabit(habit: HabitEntity) {
        viewModelScope.launch {
            DBRepository.addHabit(habit)
        }
    }
    init {
        // Підписка на дані про звички з бази
        viewModelScope.launch {
            DBRepository.getAllHabits().collect { habits ->
                _habitsList.value = habits
            }
        }
    }
    fun deleteHabit(habit: HabitEntity) {
        viewModelScope.launch {
            DBRepository.deleteHabit(habit)
        }
    }

    suspend fun getAllHabits(): Flow<List<HabitEntity>> {
        return DBRepository.getAllHabits()
    }

    fun updateSelectedState(id: Int, isDone: Boolean) {
        viewModelScope.launch {
            DBRepository.updateSelectedState(id, isDone)
        }
    }

}
