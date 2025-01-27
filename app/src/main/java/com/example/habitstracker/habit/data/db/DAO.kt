package com.example.habitstracker.habit.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.habitstracker.habit.domain.HabitEntity
import com.example.habitstracker.habit.domain.DateHabitEntity
import kotlinx.coroutines.flow.Flow

// Data Access Object
@Dao
sealed interface DAO {
    @Insert
    suspend fun addHabit(entity: HabitEntity): Long

    @Query("delete from habit_table where id=:id")
    fun deleteHabit(id: Int)

    @Query("update habit_table set isCompleted=:isDone where id=:id")
    fun updateHabitSelectState(id: Int, isDone: Boolean)

    @Query("update date_table set isCompleted=:isDone where habitId=:id")
    fun updateDateSelectState(id: Int, isDone: Boolean)

    @Transaction
    fun updateHabitAndDateSelectState(id: Int, isDone: Boolean) {
        updateHabitSelectState(id, isDone)
        updateDateSelectState(id, isDone)
    }

    @Query("select * from habit_table")
    fun getAllHabits(): Flow<List<HabitEntity>>

    @Query("select * from habit_table where name=:id")
    fun getHabitById(id: Int): HabitEntity?

    @Update
    fun updateHabit(habit: HabitEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHabitDate(habitDate: DateHabitEntity)

    @Query(
        """
    SELECT habit_table.* FROM habit_table
    JOIN date_table
    ON habit_table.id = date_table.habitId
    WHERE date_table.date = :date
"""
    ) // YYYY-MM-DD
    fun getHabitsByDate(date: String): Flow<List<HabitEntity>>


    /* @Query("SELECT * FROM habit_status_table WHERE habitId = :habitId AND date = :date")
     fun getHabitStatusForDay(habitId: Int, date: String): HabitStatusEntity?*/


}