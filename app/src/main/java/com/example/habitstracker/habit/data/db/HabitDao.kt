package com.example.habitstracker.habit.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.habitstracker.habit.domain.HabitEntity
import com.example.habitstracker.habit.domain.DateHabitEntity
import com.example.habitstracker.habit.domain.ShownHabit
import kotlinx.coroutines.flow.Flow

// Data Access Object
@Dao
sealed interface HabitDao {
    @Insert
    suspend fun insertHabit(entity: HabitEntity): Long

    @Query("delete from habit_table where id=:id")
    suspend fun deleteHabit(id: Int)

    @Query("update date_table set isCompleted=:isDone where habitId=:id AND currentDate=:selectDate")
    fun updateDateSelectState(id: Int, isDone: Boolean, selectDate: String)

    @Query("SELECT * FROM date_table ORDER BY currentDate DESC LIMIT 1")
    fun getLastAvailableDate(): DateHabitEntity?

    @Update
    suspend fun updateHabit(habit: HabitEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHabitDate(habitDate: DateHabitEntity)

    @Query(
        """
    SELECT 
        habit_table.id AS habitId,
        date_table.id AS dateHabitId,
        habit_table.name,
        habit_table.iconName,
        habit_table.colorHex,
        habit_table.days,
        habit_table.executionTime,
        habit_table.reminder,
        date_table.isCompleted AS isSelected
    FROM habit_table
    JOIN date_table
    ON habit_table.id = date_table.habitId
    WHERE date_table.currentDate = :date
    """
    )
    fun getHabitsByDate(date: String): Flow<List<HabitWithDateDb>>

    @Query("SELECT * FROM date_table WHERE habitId=:id")
    suspend fun getAllDatesByHabitId(id: Int): List<DateHabitEntity>

    @Query(
        """
        SELECT EXISTS(
            SELECT 1 FROM date_table 
            WHERE habitId = :habitId AND currentDate = :date
        )
    """
    )
     suspend fun dateExistsForHabit(habitId: Int, date: String): Boolean

    @Query("SELECT * FROM habit_table")
    fun getAllHabits(): Flow<List<HabitEntity>>

    @Query("SELECT * FROM date_table WHERE currentDate = :date")
    fun getDateHabitsFor(date: String): Flow<List<DateHabitEntity>>

    @Query("select * from date_table")
    fun getAllDateHabits(): Flow<List<DateHabitEntity>>
}