package com.olesmalysh.habitstracker.history.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.olesmalysh.habitstracker.habit.domain.DateHabitEntity
import com.olesmalysh.habitstracker.habit.domain.HabitEntity
import com.olesmalysh.habitstracker.history.domain.AchievementEntity
import kotlinx.coroutines.flow.Flow

@Dao
sealed interface HistoryDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAchievements(achievementEntityList: List<AchievementEntity>)

    @Query("DELETE FROM achievement_table")
    suspend fun clearAchievements()

    @Query("select * from achievement_table")
    suspend fun getAllAchievementsOnce(): List<AchievementEntity>

    @Query("select * from habit_table")
    fun getALlMyHabits(): Flow<List<HabitEntity>>

    @Query("select * from achievement_table LIMIT 1")
    suspend fun getAchievementOnce(): AchievementEntity?

    @Query("select * from achievement_table")
    fun getAllAchievement(): Flow<List<AchievementEntity>>

    @Query("update achievement_table set notified =:isNotified, unlockedAt =:unlockedAt where id =:id")
    suspend fun updateUnlockedDate(unlockedAt: String, isNotified: Boolean, id: Int)


    @Query("select * from date_table order by currentDate desc")
    fun getAllDatesForStreak(): Flow<List<DateHabitEntity>>

    @Delete
    suspend fun deleteHabit(habit: HabitEntity)
}