package com.example.habitstracker.history.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habitstracker.habit.domain.DateHabitEntity
import com.example.habitstracker.history.domain.AchievementEntity
import com.example.habitstracker.history.domain.HistoryRepository
import com.example.habitstracker.history.domain.achievementsList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val historyRepository: HistoryRepository
) : ViewModel() {
    private val _dateHabitsList = MutableStateFlow<List<DateHabitEntity>>(emptyList())
    val dateHabitList: StateFlow<List<DateHabitEntity>> = _dateHabitsList.asStateFlow()

    // private cold stream from repository
    private val _allAchievementsFlow = getAllAchievements()

    // public stateflow (hot) for ui
    val allAchievements: StateFlow<List<AchievementEntity>> = _allAchievementsFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = emptyList()
    )

    init {
        firstEntryDbFilling()

        viewModelScope.launch {
            getAllDatesForStreak().collect { habits ->
                _dateHabitsList.value = habits
            }
        }
    }

    suspend fun updateUnlockedDate(unlockedAt: String, isNotified: Boolean, id: Int) {
        historyRepository.updateUnlockedDate(unlockedAt, isNotified, id)
    }

    private fun getAllAchievements(): Flow<List<AchievementEntity>> {
        return historyRepository.getAllAchievements()
    }

    private fun getAllDatesForStreak(): Flow<List<DateHabitEntity>> {
        return historyRepository.getAllDatesForStreak()
    }

    private fun firstEntryDbFilling() {
        viewModelScope.launch {
            val achievements = historyRepository.getAchievementOnce()
            if (achievements == null) {
                historyRepository.insertAchievements(achievementsList)
            }
        }
    }
}