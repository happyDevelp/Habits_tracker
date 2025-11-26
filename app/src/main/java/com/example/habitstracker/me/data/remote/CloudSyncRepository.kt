package com.example.habitstracker.me.data.remote

import android.util.Log
import com.example.habitstracker.habit.domain.DateHabitEntity
import com.example.habitstracker.habit.domain.HabitEntity
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
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

    suspend fun uploadHabit(userId: String, habit: HabitEntity, dateHabit: DateHabitEntity) {
        val habitsCollection = habitsCollection(userId)
        habitsCollection.document(habit.id.toString())
            .set(habit)
            .await()
    }

    suspend fun uploadDateHabit(userId: String, dateHabit: DateHabitEntity) {
        val dateDatesCollection = datesCollection(userId)
        // dateDatesCollection.document("${dateHabit.id}_${dateHabit.currentDate}")
        dateDatesCollection.document(dateHabit.id.toString())
            .set(dateHabit)
            .await()
    }

    suspend fun updateHabit(userId: String, habit: HabitEntity) {
        val col = habitsCollection(userId)
        col.document(habit.id.toString())
            .set(habit, SetOptions.merge())
            .await()
    }

    suspend fun updateDateHabit(
        userId: String,
        dateHabitId: String,
        date: String,
        isDone: Boolean
    ) {
        val col = datesCollection(userId)
        col.document(dateHabitId)
            .set(
                mapOf("completed" to isDone),
                SetOptions.merge()
            )
            .await()
    }

    suspend fun deleteHabit(userId: String, habitId: String) {
        val habitCollection = habitsCollection(userId)
        habitCollection.document(habitId)
            .delete()
            .await()

        val dateHabitCollection = datesCollection(userId)
        dateHabitCollection.document(habitId)
            .delete()
            .await()
    }

    suspend fun deleteDateHabit(userId: String, dateHabitId: String) {

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


    // ---------- OTHER METHODS ----------

    suspend fun clearCloud(userId: String): Boolean {
        return try {
            // delete all habitsEntity
            val habitsCol = habitsCollection(userId)
            val habitsSnapshot = habitsCol.get().await()
            habitsSnapshot.documents.forEach { doc ->
                habitsCol.document(doc.id).delete().await()
            }

            // delete all DateHabits
            val datesCol = datesCollection(userId)
            val datesSnapshot = datesCol.get().await()
            datesSnapshot.documents.forEach { doc ->
                datesCol.document(doc.id).delete().await()
            }
            true
        } catch (e: Exception) {
            Log.e("SYNC", "clearCloud failed", e)
            false
        }
    }
}