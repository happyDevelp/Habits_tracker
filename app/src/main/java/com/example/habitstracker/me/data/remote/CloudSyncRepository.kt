package com.example.habitstracker.me.data.remote

import com.example.habitstracker.habit.domain.DateHabitEntity
import com.example.habitstracker.habit.domain.HabitEntity
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class CloudSyncRepository @Inject constructor(private val firestore: FirebaseFirestore) {

    private fun userDoc(userId: String): DocumentReference {
        return firestore.collection("users").document(userId)
    }

    private fun habitsCollection(userId: String): CollectionReference {
        return userDoc(userId).collection("habits")
    }

    private fun datesCollection(userId: String): CollectionReference {
        return userDoc(userId).collection("dates")
    }


    // ---------- UPLOAD ----------

    suspend fun uploadHabits(userId: String, habits: List<HabitEntity>) {
        val col = habitsCollection(userId)
        habits.forEach { habit ->
            col.document(habit.id.toString()).set(habit).await()
        }
    }

    suspend fun uploadDates(userId: String, dates: List<DateHabitEntity>) {
        val col = datesCollection(userId)
        dates.forEach { date ->
            col.document(date.id.toString()).set(date).await()
        }
    }


    // ---------- DOWNLOAD ----------

    suspend fun downloadHabits(userId: String): List<HabitEntity> {
        val snapshot = habitsCollection(userId).get().await()
        return snapshot.toObjects(HabitEntity::class.java)
    }

    suspend fun downloadDates(userId: String): List<DateHabitEntity> {
        val snapshot = datesCollection(userId).get().await()
        return snapshot.toObjects(DateHabitEntity::class.java)
    }

}