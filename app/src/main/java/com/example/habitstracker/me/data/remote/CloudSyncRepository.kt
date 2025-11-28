package com.example.habitstracker.me.data.remote

import android.util.Log
import com.example.habitstracker.habit.domain.DateHabitEntity
import com.example.habitstracker.habit.domain.HabitEntity
import com.example.habitstracker.history.domain.AchievementEntity
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
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

    private fun achievementsCollection(userId: String): CollectionReference {
        return userDoc(userId).collection("achievements")
    }

    // ---------- PUSH ----------

    /** HabitEntity */
    suspend fun pushHabit(userId: String, habit: HabitEntity) {
        val habitsCollection = habitsCollection(userId)
        habitsCollection.document(habit.id.toString())
            .set(habit)
            .await()
    }

    // overloaded
    suspend fun pushHabit(userId: String, habits: List<HabitEntity>) {
        val db = Firebase.firestore
        val batch = db.batch()

        val dateHabitsCollection = habitsCollection(userId)
        habits.forEach { habit ->
            val docRef = dateHabitsCollection.document(habit.id.toString())
            // 2. FILLING THE BOX (locally)
            batch.set(docRef, habit)
        }
        batch.commit().await()
    }

    suspend fun updateHabit(userId: String, habit: HabitEntity) {
        val col = habitsCollection(userId)
        col.document(habit.id.toString())
            .set(habit, SetOptions.merge())
            .await()
    }


    /** DateHabitEntity */
    suspend fun pushDateHabit(userId: String, dateHabit: DateHabitEntity) {
        val dateDatesCollection = datesCollection(userId)
        dateDatesCollection.document(dateHabit.id.toString())
            .set(dateHabit)
            .await()
    }

    // overloaded
    suspend fun pushDateHabit(userId: String, dateHabits: List<DateHabitEntity>) {
        val db = Firebase.firestore
        val batch = db.batch()

        val dateHabitsCollection = datesCollection(userId)
        dateHabits.forEach { dateHabit ->
            val docRef = dateHabitsCollection.document(dateHabit.id.toString())
            batch.set(docRef, dateHabit)
        }
        batch.commit().await()
    }

    /** AchievementEntity */
    suspend fun pushAchievement(userId: String, achievement: AchievementEntity) {
        achievementsCollection(userId)
            .document(achievement.id.toString())
            .set(achievement)
            .await()
    }

    suspend fun pushAchievements(userId: String, achievements: List<AchievementEntity>) {
        val db = Firebase.firestore
        val batch = db.batch()

        val achievementsCol = achievementsCollection(userId)
        achievements.forEach { achievement ->
            val docRef = achievementsCol.document(achievement.id.toString())
            batch.set(docRef, achievement)
        }
        batch.commit().await()
    }

    suspend fun updateSelectState(
        userId: String,
        dateHabitId: String,
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


    // delete (HabitEntity and DateHabitEntity together)
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

    // ---------- GET ----------

    suspend fun getHabits(userId: String): List<HabitEntity> {
        val snapshot = habitsCollection(userId).get().await()
        return snapshot.toObjects(HabitEntity::class.java)
    }

    suspend fun getDates(userId: String): List<DateHabitEntity> {
        val snapshot = datesCollection(userId).get().await()
        return snapshot.toObjects(DateHabitEntity::class.java)
    }

    suspend fun getAchievements(userId: String): List<AchievementEntity> {
        val snapshot = achievementsCollection(userId).get().await()
        return snapshot.toObjects(AchievementEntity::class.java)
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