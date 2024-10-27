package com.example.habitstracker.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

//Data Access Object
@Dao
interface DAO {

    @Insert
    suspend fun addHabit(entity: HabitEntity)

    @Delete
    fun deleteHabit(entity: HabitEntity)

    @Query("select * from habit_table")
    fun getAllHabits(): Flow<List<HabitEntity>>

    @Query("update habit_table set isDone=:isDone where id=:id")
    fun updateSelectedState(id: Int, isDone: Boolean)

}