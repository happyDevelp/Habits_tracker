package com.example.habitstracker.data.db.repository

import com.example.habitstracker.data.db.DAO
import com.example.habitstracker.data.db.HabitEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

interface DBRepository {

    suspend fun addHabit(habit: HabitEntity)

    suspend fun deleteHabit(habit: HabitEntity)

    suspend fun getAllHabits(): Flow<List<HabitEntity>>

    suspend fun updateSelectedState(id: Int, isDone: Boolean)

    suspend fun getHabitByName(name: String): HabitEntity?

    suspend fun getHabitById(id: Int): HabitEntity?

    suspend fun updateHabit(habit: HabitEntity)
}

class RepositoryImpl(private val dao: DAO) : DBRepository {
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

    override suspend fun updateSelectedState(id: Int, isDone: Boolean) {
        return withContext(Dispatchers.IO) {
            dao.updateSelectedState(id, isDone)
        }
    }

    override suspend fun getHabitByName(name: String): HabitEntity? {
        return withContext(Dispatchers.IO) {
            dao.getHabitByName(name)
        }
    }

    override suspend fun getHabitById(id: Int): HabitEntity? {
        return withContext(Dispatchers.IO) {
            dao.getHabitById(id)
        }
    }

    override suspend fun updateHabit(habit: HabitEntity) {
        return withContext(Dispatchers.IO) {
            dao.updateHabit(habit)
        }
    }
}

