package com.example.habitstracker.data.db.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.habitstracker.data.db.repository.DBRepository

class HabitViewModelFactory(
    private val DBRepository: DBRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HabitViewModel::class.java)) {
            return HabitViewModel(DBRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}