package com.example.habitstracker.me.data.remote.firebase

import android.util.Log
import com.example.habitstracker.me.domain.model.FriendEntry
import com.example.habitstracker.me.domain.model.FriendRequest
import com.example.habitstracker.me.domain.model.UserProfile
import com.example.habitstracker.me.domain.model.UserStats
import com.google.firebase.Timestamp
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

/**
 * Centralizes Firestore access for the users/{uid} tree.
 */
class UserFirebaseDataSource @Inject constructor(
    private val firestore: FirebaseFirestore
) {

    private fun userDoc(userId: String): DocumentReference =
        firestore.collection("users").document(userId)

    private fun profileDoc(userId: String): DocumentReference =
        userDoc(userId).collection("profile").document("main")

    private fun statsDoc(userId: String): DocumentReference =
        userDoc(userId).collection("stats").document("stats")

    private fun friendsCollection(userId: String): CollectionReference =
        userDoc(userId).collection("friends")

    private fun requestsCollection(userId: String): CollectionReference =
        userDoc(userId).collection("friendRequests")

    fun observeUserProfile(userId: String): Flow<UserProfile?> = callbackFlow {
        val registration = profileDoc(userId).addSnapshotListener { snapshot, _ ->
            val profile = snapshot?.toObject(UserProfile::class.java)
            trySend(profile)
        }
        awaitClose { registration.remove() }
    }

    suspend fun ensureUserProfile(userId: String, displayName: String?, avatarUrl: String?) {
        val doc = profileDoc(userId)
        val snapshot = doc.get().await()

        if (!snapshot.exists()) {
            val profile = UserProfile(
                displayName = displayName ?: "User",
                avatarUrl = avatarUrl,
                friendCode = generateFriendCodeFromUid(userId)
            )
            doc.set(profile).await()
        } else {
            val current = snapshot.toObject(UserProfile::class.java) ?: return
            val updates = mutableMapOf<String, Any>()

            if (!displayName.isNullOrBlank() && displayName != current.displayName) {
                updates["displayName"] = displayName
            }
            if (avatarUrl != null && avatarUrl != current.avatarUrl) {
                updates["avatarUrl"] = avatarUrl
            }

            if (updates.isNotEmpty()) {
                doc.update(updates).await()
            }
        }
    }

    fun observeUserStats(userId: String): Flow<UserStats?> = callbackFlow {
        val registration = statsDoc(userId).addSnapshotListener { snapshot, _ ->
            val stats = snapshot?.toObject(UserStats::class.java)
            trySend(stats)
        }
        awaitClose { registration.remove() }
    }

    suspend fun pushUserStats(userId: String, stats: UserStats) {
        val data = stats.copy(updatedAt = Timestamp.now())
        statsDoc(userId).set(data).await()
    }

    fun observeFriends(userId: String): Flow<List<FriendEntry>> = callbackFlow {
        val registration = friendsCollection(userId)
            .addSnapshotListener { snap, _ ->
                val list = snap?.documents.orEmpty().mapNotNull { doc ->
                    FriendEntry(
                        friendUserId = doc.id,
                        friendDisplayName = doc.getString("friendDisplayName").orEmpty(),
                        friendAvatarUrl = doc.getString("friendAvatarUrl"),
                        friendSince = doc.getLong("friendSince") ?: 0L
                    )
                }
                trySend(list)
            }
        awaitClose { registration.remove() }
    }

    fun observeIncomingRequests(userId: String): Flow<List<FriendRequest>> = callbackFlow {
        val registration = requestsCollection(userId)
            .addSnapshotListener { snap, _ ->
                val list = snap?.documents.orEmpty().map { doc ->
                    FriendRequest(
                        id = doc.id,
                        fromUserId = doc.getString("fromUserId").orEmpty(),
                        fromDisplayName = doc.getString("fromDisplayName").orEmpty(),
                        fromAvatarUrl = doc.getString("fromAvatarUrl"),
                        sentAt = doc.getLong("sentAt") ?: 0L
                    )
                }
                trySend(list)
            }
        awaitClose { registration.remove() }
    }

    suspend fun sendFriendRequest(
        fromUser: UserProfile,
        fromUserId: String,
        targetFriendCode: String
    ) {
        val query = firestore
            .collectionGroup("profile")
            .whereEqualTo("friendCode", targetFriendCode.uppercase())
            .limit(1)
            .get()
            .await()

        val profileDoc = query.documents.firstOrNull()
            ?: throw IllegalArgumentException("User with this code not found")

        val targetUserDoc = profileDoc.reference.parent.parent
            ?: throw IllegalStateException("Profile without parent user doc")

        val targetUserId = targetUserDoc.id
        if (targetUserId == fromUserId) {
            throw IllegalArgumentException("You can't add yourself")
        }

        val requestDoc = requestsCollection(targetUserId).document(fromUserId)

        val requestData = mapOf(
            "fromUserId" to fromUserId,
            "fromDisplayName" to fromUser.displayName,
            "fromAvatarUrl" to fromUser.avatarUrl,
            "sentAt" to System.currentTimeMillis()
        )

        requestDoc.set(requestData).await()
    }

    suspend fun acceptFriendRequest(currentUserId: String, request: FriendRequest) {
        val otherUserId = request.fromUserId
        val nowMillis = System.currentTimeMillis()

        val myProfile = profileDoc(currentUserId).get().await().toObject(UserProfile::class.java)
            ?: return

        val batch = firestore.batch()

        val myFriendDoc = friendsCollection(currentUserId).document(otherUserId)
        val theirFriendDoc = friendsCollection(otherUserId).document(currentUserId)

        val myFriendEntry = mapOf(
            "friendUserId" to otherUserId,
            "friendDisplayName" to request.fromDisplayName,
            "friendAvatarUrl" to request.fromAvatarUrl,
            "friendSince" to nowMillis
        )

        val theirFriendEntry = mapOf(
            "friendUserId" to currentUserId,
            "friendDisplayName" to myProfile.displayName,
            "friendAvatarUrl" to myProfile.avatarUrl,
            "friendSince" to nowMillis
        )

        batch.set(myFriendDoc, myFriendEntry)
        batch.set(theirFriendDoc, theirFriendEntry)

        val requestDoc = requestsCollection(currentUserId).document(request.id)
        batch.delete(requestDoc)

        batch.commit().await()
    }

    suspend fun rejectFriendRequest(currentUserId: String, requestId: String) {
        requestsCollection(currentUserId).document(requestId).delete().await()
    }

    suspend fun getFriendStats(friendUserId: String): UserStats? {
        Log.d("FriendsRepositoryImpl", "getFriendStats: $friendUserId")
        val statsDoc = statsDoc(friendUserId).get().await()
        Log.d("FriendsRepositoryImpl", "getFriendStats: ${statsDoc.exists()}")
        return statsDoc.toObject(UserStats::class.java)
    }
}

private const val FRIEND_CODE_ALPHABET = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789"

private fun generateFriendCodeFromUid(uid: String, length: Int = 8): String {
    var value = uid.hashCode().toUInt().toLong()
    val base = FRIEND_CODE_ALPHABET.length
    val sb = StringBuilder()

    repeat(length) {
        val index = (value % base).toInt().coerceIn(0, base - 1)
        sb.append(FRIEND_CODE_ALPHABET[index])
        value /= base
    }

    return sb.toString()
        .chunked(4)
        .joinToString("-")
}
