package com.example.habitstracker.data.db.repository

import com.example.habitstracker.data.db.DAO
import com.example.habitstracker.data.db.HabitDatabase
import com.example.habitstracker.data.db.HabitEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

interface Repository {

    suspend fun addHabit(habit: HabitEntity)

    suspend fun deleteHabit(habit: HabitEntity)

    suspend fun getAllHabits(): Flow<List<HabitEntity>>
}

class RepositoryImpl(
    private val dao: DAO,
) : Repository {
    override suspend fun addHabit(habit: HabitEntity) {
        withContext(Dispatchers.IO) {
            dao.addHabit(habit)
        }
    }

    override suspend fun deleteHabit(habit: HabitEntity) {
        withContext(Dispatchers.IO) {
            dao.deleteHabit(habit)
        }
    }

    override suspend fun getAllHabits(): Flow<List<HabitEntity>> {
        return withContext(Dispatchers.IO) {
            dao.getAllHabits()
        }
    }

}

fun provideMyRepository(mydb:HabitDatabase) :Repository {
    return RepositoryImpl(mydb.dao)
}