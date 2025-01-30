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
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import java.time.LocalDate
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
                fillMissingDates()
            }
        }
    }

    private suspend fun fillMissingDates() {
        habitsListState.value.forEach { habit ->

            if (!isTodayDatePushed(habit.id)) {
                val allDatesById = habitRepository.getAllDatesByHabitId(habit.id)
                insertHabitDate(
                    DateHabitEntity(
                        habitId = habit.id,
                        startDate = allDatesById.get(0).startDate,
                        currentDate = LocalDate.now().toString(),
                        isCompleted = false
                    )
                )
            }
        }
    }

    private suspend fun isTodayDatePushed(habitId: Int): Boolean {
        val allDates = habitRepository.getAllDatesByHabitId(habitId)
        return allDates.isNotEmpty() && allDates.last().currentDate == LocalDate.now().toString()
    }

    fun insertHabit(habit: HabitEntity, onHabitAdded: (habitId: Long) -> Unit) {
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

    suspend fun updateHabit(habit: HabitEntity) {
        return habitRepository.updateHabit(habit)
    }


    suspend fun getAllHabits(): Flow<List<HabitEntity>> {
        return habitRepository.getAllHabits()
    }

    fun insertHabitDate(dateHabit: DateHabitEntity) {
        viewModelScope.launch {
            habitRepository.insertHabitDate(dateHabit)
        }
    }

    fun updateHabitAndDateSelectState(id: Int, isDone: Boolean) {
        viewModelScope.launch {
            habitRepository.updateHabitAndDateSelectState(id, isDone)
        }
    }

    fun setHabitsForDate(date: String /*YYYY-MM-DD*/) {
        viewModelScope.launch {
            habitRepository.getHabitsByDate(date).collect { habits ->
                _habitsListState.value = habits
            }
        }
    }
}