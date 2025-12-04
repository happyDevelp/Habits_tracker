package com.example.habitstracker.me.data.remote

import android.util.Log
import com.example.habitstracker.me.domain.FriendsRepository
import com.example.habitstracker.me.domain.model.FriendEntry
import com.example.habitstracker.me.domain.model.FriendRequest
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FriendsRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : FriendsRepository {
    fun userDoc(userId: String) =
        firestore.collection("users").document(userId)


    fun profileDoc(userId: String) =
        userDoc(userId).collection("profile").document("profile")


    fun friendsCol(userId: String) =
        userDoc(userId).collection("friends")

    fun requestsCol(userId: String) =
        userDoc(userId).collection("friendRequests")


    /* observe */
    override fun observeFriends(userId: String): Flow<List<FriendEntry>> = callbackFlow {
        val reg = friendsCol(userId)
            .addSnapshotListener { snap, e ->
                if (e != null) {
                    trySend(emptyList())
                    return@addSnapshotListener
                }
                val list = snap?.documents.orEmpty().mapNotNull { doc ->
                    doc.toObject<FriendEntry>()?.copy(userId = doc.id)
                }
                trySend(list)
            }
        awaitClose { reg.remove() }
    }

    override fun observeIncomingRequests(userId: String): Flow<List<FriendRequest>> = callbackFlow {
        val reg = requestsCol(userId)
            .addSnapshotListener { snap, e ->
                if (e != null) {
                    trySend(emptyList())
                    return@addSnapshotListener
                }
                val list = snap?.documents.orEmpty().map { doc ->
                    FriendRequest(
                        id = doc.id, // = fromUserId
                        fromUserId = doc.getString("fromUserId").orEmpty(),
                        fromDisplayName = doc.getString("fromDisplayName").orEmpty(),
                        fromAvatarUrl = doc.getString("fromAvatarUrl"),
                        sentAt = doc.getLong("sentAt") ?: 0L
                    )
                }
                trySend(list)
            }
        awaitClose { reg.remove() }
    }

    override suspend fun sendFriendRequest(
        fromUserId: String,
        fromDisplayName: String,
        fromAvatarUrl: String?,
        targetFriendCode: String
    ) {
        Log.d("FriendsRepositoryImpl", "sendFriendRequest: code=$targetFriendCode")

        try {
            val query = firestore
                .collectionGroup("profile")
                .whereEqualTo("friendCode", targetFriendCode.uppercase())
                .limit(1)
                .get()
                .await()

            Log.d("FriendsRepositoryImpl", "sendFriendRequest: result size=${query.size()}")

            val profileDoc = query.documents.firstOrNull()
                ?: throw IllegalArgumentException("User with this code not found")

            val targetUserDoc = profileDoc.reference.parent.parent
                ?: throw IllegalStateException("Profile without parent user doc")

            val targetUserId = targetUserDoc.id
            Log.d("FriendsRepositoryImpl", "sendFriendRequest: targetUserId=$targetUserId")

            if (targetUserId == fromUserId) {
                throw IllegalArgumentException("You can't add yourself")
            }

            val requestDoc = requestsCol(targetUserId).document(fromUserId)

            val requestData = mapOf(
                "fromUserId" to fromUserId,
                "fromDisplayName" to fromDisplayName,
                "fromAvatarUrl" to fromAvatarUrl,
                "sentAt" to System.currentTimeMillis()
            )

            Log.d("FriendsRepositoryImpl", "sendFriendRequest: writing=$requestData")

            requestDoc.set(requestData).await()
            Log.d("FriendsRepositoryImpl", "sendFriendRequest: done")

        } catch (e: Exception) {
            Log.e("FriendsRepositoryImpl", "sendFriendRequest failed", e)
            throw e
        }
    }
    // -------- accept / ignore --------

    override suspend fun acceptRequest(
        currentUserId: String,
        request: FriendRequest
    ) {
        val otherUserId = request.fromUserId  // або request.id – те саме
        val nowMillis = System.currentTimeMillis()

        val myProfileSnap = profileDoc(currentUserId).get().await()
        val myName = myProfileSnap.getString("displayName").orEmpty()
        val myAvatar = myProfileSnap.getString("avatarUrl")

        val batch = firestore.batch()

        // friendEntries в обох users/{}/friends/{friendId}
        val myFriendDoc = friendsCol(currentUserId).document(otherUserId)
        val theirFriendDoc = friendsCol(otherUserId).document(currentUserId)

        val myFriendEntry = mapOf(
            "userId" to otherUserId,       // was "friendUserId"
            "displayName" to request.fromDisplayName, // was "friendDisplayName"
            "avatarUrl" to request.fromAvatarUrl,     // was "friendAvatarUrl"
            "since" to nowMillis           // was "friendSince"
        )

        val theirFriendEntry = mapOf(
            "userId" to currentUserId,
            "displayName" to myName,
            "avatarUrl" to myAvatar,
            "since" to nowMillis
        )

        batch.set(myFriendDoc, myFriendEntry)     // docId = friendUserId → also without duplicates
        batch.set(theirFriendDoc, theirFriendEntry)

        // видаляємо запит
        val requestDoc = requestsCol(currentUserId).document(request.id)
        batch.delete(requestDoc)

        batch.commit().await()
    }

    override suspend fun rejectRequest(
        currentUserId: String,
        requestId: String
    ) {
        requestsCol(currentUserId)
            .document(requestId) // = fromUserId
            .delete()
            .await()
    }

    // Help fun: Extract my profile and make a FriendEntry
    suspend fun loadMyProfileAsFriendEntry(
        myUserId: String,
        now: Timestamp
    ): FriendEntry {
        val snap = profileDoc(myUserId).get().await()
        val displayName = snap.getString("displayName").orEmpty()
        val avatarUrl = snap.getString("avatarUrl")

        return FriendEntry(
            userId = myUserId,
            displayName = displayName,
            avatarUrl = avatarUrl,
            since = now.toDate().time
        )
    }

}