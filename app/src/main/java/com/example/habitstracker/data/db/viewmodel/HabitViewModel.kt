package com.example.habitstracker.data.db.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
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

/*    private val _currentHabit = mutableStateOf<HabitEntity?>(null)
    val currentHabit: HabitEntity?
        get() = _currentHabit.value

    fun setCurrentHabit(habit: HabitEntity) {
        Log.d("HabitViewModel", "Setting current habit: $habit")
        _currentHabit.value = habit
    }*/


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

    suspend fun getHabitByName(name: String): HabitEntity? {
        return DBRepository.getHabitByName(name)
    }

    suspend fun getHabitById(id: Int): HabitEntity? {
        return DBRepository.getHabitById(id)
    }

    suspend fun updateHabit(habit: HabitEntity) {
        return DBRepository.updateHabit(habit)
    }

}
