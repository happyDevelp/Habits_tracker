package com.example.habitstracker.history.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.habitstracker.habit.domain.DateHabitEntity
import com.example.habitstracker.habit.domain.HabitEntity
import com.example.habitstracker.history.domain.AchievementEntity
import kotlinx.coroutines.flow.Flow

@Dao
sealed interface HistoryDAO {

    @Insert
    suspend fun insertAchievements(achievementEntityList: List<AchievementEntity>)

    @Query("select * from habit_table")
    fun getALlMyHabits(): Flow<List<HabitEntity>>

    @Query("select * from achievement_table LIMIT 1")
    suspend fun getAchievementOnce(): AchievementEntity?

    @Query("select * from achievement_table")
    fun getAllAchievement(): Flow<List<AchievementEntity>>

    @Query("update achievement_table set isNotified =:isNotified, unlockedAt =:unlockedAt where id =:id")
    suspend fun updateUnlockedDate(unlockedAt: String, isNotified: Boolean, id: Int)


    @Query("select * from date_table order by currentDate desc")
    fun getAllDatesForStreak(): Flow<List<DateHabitEntity>>
}