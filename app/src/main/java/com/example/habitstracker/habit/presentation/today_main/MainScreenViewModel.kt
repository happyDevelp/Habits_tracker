package com.example.habitstracker.habit.presentation.today_main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habitstracker.habit.domain.HabitEntity
import com.example.habitstracker.habit.domain.HabitRepository
import com.example.habitstracker.habit.domain.DateHabitEntity
import com.example.habitstracker.habit.domain.ShownHabit
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val habitRepository: HabitRepository
) : ViewModel() {
    private val _habitsListState = MutableStateFlow<List<ShownHabit>>(emptyList())
    val habitsListState = _habitsListState.asStateFlow()

    private val _selectedDate = MutableStateFlow(LocalDate.now())
    val selectedDate = _selectedDate.asStateFlow()

    init {
        viewModelScope.launch {
            // subscribe on data from database
            habitRepository.getHabitsByDate(_selectedDate.value.toString()).collect { habitsList ->
                _habitsListState.value = habitsList
                fillMissingDates()
            }
            getHabitsByDate(LocalDate.now().toString())
        }
    }

    fun updateSelectedDate(newDate: LocalDate) {
        _selectedDate.value = newDate
    }

    private suspend fun fillMissingDates() {
        habitsListState.value.forEach { shownHabit ->
            val allDatesById = habitRepository.getAllDatesByHabitId(shownHabit.id)

            if (allDatesById.isNotEmpty()) {
                val lastDate = LocalDate.parse(allDatesById.last().currentDate)
                val today = LocalDate.now()

                var currentDate =
                    lastDate.plusDays(1) // Start from the day after the last recorded

                while (currentDate.isBefore(today) || currentDate.isEqual(today)) {
                    insertHabitDate(
                        DateHabitEntity(
                            habitId = shownHabit.id,
                            currentDate = currentDate.toString(),
                            isCompleted = false
                        )
                    )
                    currentDate = currentDate.plusDays(1)
                }
            }
        }
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

    fun insertHabitDate(dateHabit: DateHabitEntity) {
        viewModelScope.launch {
            habitRepository.insertHabitDate(dateHabit)
        }
    }

    fun updateHabitAndDateSelectState(id: Int, isDone: Boolean, selectDate: String) {
        viewModelScope.launch {
            habitRepository.updateHabitAndDateSelectState(id, isDone, selectDate)
        }
    }

    fun getHabitsByDate(date: String /*YYYY-MM-DD*/) {
        viewModelScope.launch {
            habitRepository.getHabitsByDate(date).collect { shownHabits ->
                _habitsListState.value = shownHabits
            }
        }
    }
}