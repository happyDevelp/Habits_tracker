package com.example.habitstracker.habit.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.habitstracker.habit.domain.HabitEntity
import com.example.habitstracker.habit.domain.HabitStatusEntity
import kotlinx.coroutines.flow.Flow

// @Query("insert into habit_table values (entity)")
// Data Access Object
@Dao
sealed interface DAO {
    @Insert
    suspend fun addHabit(entity: HabitEntity)

    @Delete
    fun deleteHabit(entity: HabitEntity)

    @Query("select * from habit_table")
    fun getAllHabits(): Flow<List<HabitEntity>>

    @Query("update habit_table set isDone=:isDone where id=:id")
    fun updateSelectedState(id: Int, isDone: Boolean)

    @Query("select * from habit_table where name=:name")
    fun getHabitByName(name: String): HabitEntity?

    @Query("select * from habit_table where name=:id")
    fun getHabitById(id: Int): HabitEntity?

    @Update
    fun updateHabit(habit: HabitEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHabitStatus(status: HabitStatusEntity)

    @Query("SELECT * FROM habit_status_table WHERE date = :date")
    fun getHabitStatusesByDate(date: String): Flow<List<HabitStatusEntity>>

    @Query("SELECT * FROM habit_status_table WHERE habitId = :habitId AND date = :date")
    fun getHabitStatusForDay(habitId: Int, date: String): HabitStatusEntity?

    @Update
    suspend fun updateHabitStatus(status: HabitStatusEntity)
}