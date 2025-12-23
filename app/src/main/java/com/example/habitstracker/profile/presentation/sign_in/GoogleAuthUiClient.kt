package com.example.habitstracker.profile.presentation.sign_in

import android.content.Context
import android.util.Log
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.GetCredentialException
import com.example.habitstracker.R
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await


class GoogleAuthUiClient(
    private val context: Context,
) {
    // ----------------------------
    //  AUTH STATE as Flow<UserData?>
    // ----------------------------
    val authState: Flow<UserData?> = callbackFlow {

        val listener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            val u = firebaseAuth.currentUser
            val mapped = u?.let {
                UserData(
                    userId = it.uid,
                    email = it.email,
                    userName = it.displayName ?: "",
                    profilePictureUrl = it.photoUrl?.toString()
                )
            }
            trySend(mapped)
        }

        auth.addAuthStateListener(listener)

        awaitClose {
            auth.removeAuthStateListener(listener)
        }
    }

    private val auth = FirebaseAuth.getInstance()
    private val credentialManager = CredentialManager.create(context)

    suspend fun signIn(): Boolean {
        // 1) We describe that we want Google ID Token from the user
        val googleInOption = GetGoogleIdOption.Builder()
            .setServerClientId(context.getString(R.string.web_client_id))
            .setFilterByAuthorizedAccounts(false) // show all accounts, not just those already logged in
            .build()

        // 2) Forming a general request for Credential Manager
        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleInOption)
            .build()

        return try {
            // 3) Open the account selection system and wait for the result
            val response: GetCredentialResponse = credentialManager.getCredential(context, request)

            // 4) Getting Google ID Token from the answer
            val googleCred = GoogleIdTokenCredential.createFrom(response.credential.data)
            val idToken = googleCred.idToken

            // 5) Swap ID Token for Firebase Credit
            val firebaseCred = GoogleAuthProvider.getCredential(idToken, null)

            // 6) Логін у Firebase
            val task = auth.signInWithCredential(firebaseCred)
            task.await()

            true
        } catch (e: GetCredentialException) {
            Log.e("GoogleAuth", "Credential error: ${e.errorMessage}", e)
            false


        } catch (t: Throwable) {
            Log.e("GoogleAuth", "Unexpected sign-in error", t)
            false
        }
    }

    fun signOut() {
        auth.signOut()
    }

/*    suspend fun deleteAccount(): Boolean {
        val user = auth.currentUser ?: return false

        return try {
            user.delete().await()
            true
        } catch (e: FirebaseAuthRecentLoginRequiredException) {
            // Firebase requires "fresh" authorization before deleting the account
            Log.e("GoogleAuth", "Recent login required before deleting account", e)
            false
        } catch (e: Exception) {
            Log.e("GoogleAuth", "Error deleting account", e)
            false
        }
    }*/

    fun getSignedInUser(): UserData? {
        val u = auth.currentUser ?: return null
        return UserData(
            userId = u.uid,
            email = u.email,
            userName = u.displayName,
            profilePictureUrl = u.photoUrl?.toString()
        )
    }
}

data class UserData(
    val userId: String,
    val email: String?,
    val userName: String?,
    val profilePictureUrl: String?
)