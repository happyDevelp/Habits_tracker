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
sealed interface HabitDao {
    // ----- INSERT / UPDATE -----

    @Insert
    suspend fun insertHabit(entity: HabitEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHabits(entities: List<HabitEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHabitDate(habitDate: DateHabitEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDates(dates: List<DateHabitEntity>)

    @Update
    suspend fun updateHabit(habit: HabitEntity)


    // ----- DELETE / CLEAR -----

    @Query("DELETE FROM habit_table WHERE id = :id")
    suspend fun deleteHabit(id: Int)

    @Query("DELETE FROM habit_table")
    suspend fun clearHabits()

    @Query("DELETE FROM date_table")
    suspend fun clearDates()


    // ----- SYNC: TRANSACTION -----

    @Transaction
    suspend fun replaceAllFromCloud(
        habits: List<HabitEntity>,
        dates: List<DateHabitEntity>
    ) {
        // 1) clean the dates, then the habits (so as not to run into the FK)
        clearDates()
        clearHabits()

        // 2) first insert habits, then give (so that FK is valid)
        insertHabits(habits)
        insertDates(dates)
    }


    // ----- DATE QUERIES -----

    @Query("UPDATE date_table SET completed = :isDone WHERE habitId = :id AND currentDate = :selectDate")
    fun updateDateSelectState(id: Int, isDone: Boolean, selectDate: String)

    @Query("SELECT * FROM date_table ORDER BY currentDate DESC LIMIT 1")
    fun getLastAvailableDate(): DateHabitEntity?

    @Query("SELECT * FROM date_table WHERE habitId = :id")
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


    // ----- HABITS + FLOWS -----

    @Query("SELECT * FROM habit_table")
    fun getAllHabits(): Flow<List<HabitEntity>>

    @Query("SELECT * FROM date_table WHERE currentDate = :date")
    fun getDateHabitsFor(date: String): Flow<List<DateHabitEntity>>

    @Query("SELECT * FROM date_table")
    fun getAllDateHabits(): Flow<List<DateHabitEntity>>

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
            date_table.completed AS isSelected
        FROM habit_table
        JOIN date_table
        ON habit_table.id = date_table.habitId
        WHERE date_table.currentDate = :date
        """
    )
    fun getHabitsByDate(date: String): Flow<List<HabitWithDateDb>>
}