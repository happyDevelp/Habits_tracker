package com.example.habitstracker.ui.screens.create_own_habit

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CreateOwnHabitViewModel(): ViewModel() {
    var _selectedDays = MutableLiveData<String?>(null)

    fun updateSelectedDays(days: String) {
        _selectedDays.value = days
    }


}