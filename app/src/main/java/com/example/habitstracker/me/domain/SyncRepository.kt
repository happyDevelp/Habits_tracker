package com.example.habitstracker.me.domain

interface SyncRepository {
    suspend fun syncFromCloud(userId: String): Boolean
    suspend fun syncToCloud(userId: String): Boolean

    suspend fun testDeleteLocalData()

}