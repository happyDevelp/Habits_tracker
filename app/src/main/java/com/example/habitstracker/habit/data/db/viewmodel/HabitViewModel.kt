package com.example.habitstracker.habit.data.db.viewmodel

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
class HabitViewModel @Inject constructor(val HabitRepository: HabitRepository) : ViewModel() {

    private val _habitsList = MutableStateFlow<List<HabitEntity>>(emptyList())
    val habitsList: StateFlow<List<HabitEntity>> = _habitsList

/*    private val _currentHabit = mutableStateOf<HabitEntity?>(null)
    val currentHabit: HabitEntity?
        get() = _currentHabit.value

    fun setCurrentHabit(habit: HabitEntity) {
        Log.d("HabitViewModel", "Setting current habit: $habit")
        _currentHabit.value = habit
    }*/


    fun addHabit(habit: HabitEntity) {
        viewModelScope.launch {
            HabitRepository.addHabit(habit)
        }
    }
    init {
        // Підписка на дані про звички з бази
        viewModelScope.launch {
            HabitRepository.getAllHabits().collect { habits ->
                _habitsList.value = habits
            }
        }
    }
    fun deleteHabit(habit: HabitEntity) {
        viewModelScope.launch {
            HabitRepository.deleteHabit(habit)
        }
    }

    suspend fun getAllHabits(): Flow<List<HabitEntity>> {
        return HabitRepository.getAllHabits()
    }

    fun updateSelectedState(id: Int, isDone: Boolean) {
        viewModelScope.launch {
            HabitRepository.updateSelectedState(id, isDone)
        }
    }

    suspend fun getHabitByName(name: String): HabitEntity? {
        return HabitRepository.getHabitByName(name)
    }

    suspend fun getHabitById(id: Int): HabitEntity? {
        return HabitRepository.getHabitById(id)
    }

    suspend fun updateHabit(habit: HabitEntity) {
        return HabitRepository.updateHabit(habit)
    }

}
