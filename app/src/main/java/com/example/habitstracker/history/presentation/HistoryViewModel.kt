package com.example.habitstracker.history.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habitstracker.habit.domain.DateHabitEntity
import com.example.habitstracker.habit.domain.HabitEntity
import com.example.habitstracker.habit.presentation.today_main.components.UnlockedAchievement
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
    private val repository: HistoryRepository
) : ViewModel() {
    private val _dateHabitsList = MutableStateFlow<List<DateHabitEntity>>(emptyList())
    val dateHabitList: StateFlow<List<DateHabitEntity>> = _dateHabitsList.asStateFlow()

    private val _myHabits = getAllMyHabits()

    val myHabits = _myHabits.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = emptyList()
    )

    private fun getAllMyHabits(): Flow<List<HabitEntity>> {
        return repository.getAllMyHabits()
    }

    // private cold stream from repository
    private val _allAchievementsFlow = getAllAchievements()

    // public stateflow (hot) for ui
    val allAchievements: StateFlow<List<AchievementEntity>> = _allAchievementsFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = emptyList()
    )

    private val _unlockedAchievementFlow = MutableStateFlow<UnlockedAchievement?>(null)
    val unlockedAchievement: StateFlow<UnlockedAchievement?> = _unlockedAchievementFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = null
    )

    init {
        firstEntryDbFilling()

        viewModelScope.launch {
            getAllDatesForStreak().collect { habits ->
                _dateHabitsList.value = habits
            }
        }
    }

    fun onAchievementUnlocked(achievement: UnlockedAchievement) {
        _unlockedAchievementFlow.value = UnlockedAchievement(
            iconRes = achievement.iconRes,
            target = achievement.target,
            description = achievement.description,
            textPadding = achievement.textPadding
        )
    }

    fun clearUnlockedAchievement() {
        _unlockedAchievementFlow.value = null
    }

    suspend fun updateUnlockedDate(unlockedAt: String, isNotified: Boolean, id: Int) {
        repository.updateUnlockedDate(unlockedAt, isNotified, id)
    }

    private fun getAllAchievements(): Flow<List<AchievementEntity>> {
        return repository.getAllAchievements()
    }

    private fun getAllDatesForStreak(): Flow<List<DateHabitEntity>> {
        return repository.getAllDatesForStreak()
    }

    private fun firstEntryDbFilling() {
        viewModelScope.launch {
            val achievements = repository.getAchievementOnce()
            if (achievements == null) {
                repository.insertAchievements(achievementsList)
            }
        }
    }
}