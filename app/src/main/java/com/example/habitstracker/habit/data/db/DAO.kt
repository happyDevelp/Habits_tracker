package com.example.habitstracker.habit.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.habitstracker.habit.domain.HabitEntity
import com.example.habitstracker.habit.domain.DateHabitEntity
import com.example.habitstracker.habit.domain.ShownHabit
import kotlinx.coroutines.flow.Flow

// Data Access Object
@Dao
sealed interface DAO {
    @Insert
    fun insertHabit(entity: HabitEntity): Long

    @Query("delete from habit_table where id=:id")
    fun deleteHabit(id: Int)

    /*@Query("update habit_table set isCompleted=:isDone where id=:id")
    fun updateHabitSelectState(id: Int, isDone: Boolean)*/

    @Query("update date_table set isCompleted=:isDone where habitId=:id AND currentDate=:selectDate")
    fun updateDateSelectState(id: Int, isDone: Boolean, selectDate: String)

    @Transaction
    fun updateHabitAndDateSelectState(id: Int, isDone: Boolean, selectDate: String) {
        //updateHabitSelectState(id, isDone)
        updateDateSelectState(id, isDone, selectDate)
    }
    @Query("SELECT * from habit_table")
    fun getAllHabits(): Flow<List<HabitEntity>>

    @Update
    fun updateHabit(habit: HabitEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHabitDate(habitDate: DateHabitEntity)

    @Query(
        """
    SELECT habit_table.*, date_table.isCompleted as isSelected FROM habit_table
    JOIN date_table
    ON habit_table.id = date_table.habitId
    WHERE date_table.currentDate = :date
"""
    ) // YYYY-MM-DD
    fun getHabitsByDate(date: String): Flow<List<ShownHabit>>

    @Query("SELECT * FROM date_table WHERE habitId=:id")
    fun getAllDatesByHabitId(id: Int): List<DateHabitEntity>
}