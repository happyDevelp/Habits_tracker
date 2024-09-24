package com.example.habitstracker.data.db

import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

//Data Access Object
interface DAO {

    @Insert
    suspend fun addHabit(entity: HabitEntity)

    @Query("select * from habit_table")
    fun getAllHabits(): Flow<List<HabitEntity>>

}