package com.example.habitstracker.habit.presentation.today_main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habitstracker.habit.domain.DateHabitEntity
import com.example.habitstracker.habit.domain.HabitEntity
import com.example.habitstracker.habit.domain.HabitRepository
import com.example.habitstracker.habit.domain.ShownHabit
import com.example.habitstracker.habit.domain.mapToShownHabits
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val habitRepository: HabitRepository
) : ViewModel() {
    private val _habitsListState = MutableStateFlow<List<ShownHabit>>(emptyList())
    val habitsListState = _habitsListState.asStateFlow()

    private val lastDateInDb = runBlocking {
        getLastAvailableDate()?.let { LocalDate.parse(it.currentDate) }
            ?: LocalDate.now()
    }

    private val _selectedDate = MutableStateFlow(LocalDate.now())
    val selectedDate = _selectedDate.asStateFlow()

    init {
        preloadAndFillAllHabits() // immediately try to get the missing dates
        observeHabitsForSelectedDate()
    }


    private fun preloadAndFillAllHabits() {
        viewModelScope.launch {

            val habits = getHabitsByDate(lastDateInDb.toString()).first() // take first value instead of collect
            _habitsListState.value = habits
            fillMissingDates()

/*            getHabitsByDate(lastDateInDb.toString()).collect { habits ->
                // set the last known  data to fillMissingDates() had access
                println("cccc Habits for $lastDateInDb.toString(): $habits")
                _habitsListState.value = habits
                fillMissingDates()
            }*/
        }
    }

    private suspend fun fillMissingDates() {
        val today = LocalDate.now()

        habitsListState.value.forEach { shownHabit ->
            val allDatesById = getAllDatesByHabitId(shownHabit.id)

            // If there is no dates at all - we create for 'today'
            if (allDatesById.isEmpty()) {
                insertHabitDate(
                    DateHabitEntity(
                        habitId = shownHabit.id,
                        currentDate = today.toString(),
                        isCompleted = false
                    )
                )
                return@forEach
            }

            val lastDate = LocalDate.parse(allDatesById.last().currentDate)
            var currentDate = lastDate.plusDays(1)

            while (!currentDate.isAfter(today)) {
                val alreadyExists = dateExistsForHabit(shownHabit.id, currentDate.toString())
                if (!alreadyExists) {
                    insertHabitDate(
                        DateHabitEntity(
                            habitId = shownHabit.id,
                            currentDate = currentDate.toString(),
                            isCompleted = false
                        )
                    )
                }
                currentDate = currentDate.plusDays(1)
            }
        }
    }

    private fun observeHabitsForSelectedDate() {
        viewModelScope.launch {
            selectedDate.collectLatest { date ->
                getHabitsByDate(date.toString()).collect { habits ->
                    //println("aaaa Habits for $date: $habits")
                    _habitsListState.value = habits
                }
            }
        }
    }

    fun updateSelectedDate(newDate: LocalDate) {
        _selectedDate.value = newDate
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

    fun updateDateSelectState(id: Int, isDone: Boolean, selectDate: String) {
        viewModelScope.launch {
            println("bbbb Updated habitId=$id, isDone=$isDone, date=$selectDate")
            habitRepository.updateDateSelectState(id, isDone, selectDate)
            val habits = getHabitsByDate(selectDate).first() // Синхронний запит для перевірки
            println("bbbb After update, habits for $selectDate: $habits")
        }
    }

    private fun getHabitsByDate(date: String): Flow<List<ShownHabit>> {
        return habitRepository.getHabitsByDate(date)
    }

    private suspend fun dateExistsForHabit(habitId: Int, date: String): Boolean {
        return habitRepository.dateExistsForHabit(habitId, date)
    }

    private suspend fun getLastAvailableDate(): DateHabitEntity? {
        return habitRepository.getLastAvailableDate()
    }

    private suspend fun getAllDatesByHabitId(id: Int): List<DateHabitEntity> {
        return habitRepository.getAllDatesByHabitId(id)
    }
}