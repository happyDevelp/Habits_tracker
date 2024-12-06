package com.example.habitstracker.ui.screens.edit_habit.editViewModel

import androidx.lifecycle.ViewModel
import com.example.habitstracker.ui.screens.edit_habit.components.SelectedDay
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class EditViewModel @Inject constructor(): ViewModel() {

    private val _currentSelectedHabitList = MutableStateFlow<List<SelectedDay>>(emptyList())
    val currentSelectedHabitList: StateFlow<List<SelectedDay>> = _currentSelectedHabitList

    fun updateSelectedHabitList(newList: List<SelectedDay>) {
        _currentSelectedHabitList.value = newList
    }

  /*  // Метод для оновлення конкретного елемента в списку
    fun toggleDaySelection(day: String) {
        val updatedList = _currentSelectedHabitList.value.map { selectedDay ->
            if (selectedDay.day == day) {
                selectedDay.copy(isSelect = !selectedDay.isSelect)
            } else {
                selectedDay
            }
        }
        _currentSelectedHabitList.value = updatedList*//*
    }*/

}