package com.example.habitstracker.me.presentation.sign_in

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
import kotlinx.coroutines.tasks.await


class GoogleAuthUiClient(
    private val context: Context,
) {
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


/*class GoogleAuthUiClass(
    private val context: Context,
    private val oneTapClient: SignInClient
) {
    private val auth = Firebase.auth

    suspend fun signIn(): IntentSender? {
        val result = try {
            oneTapClient.beginSignIn(

            )
        } catch (e: Exception) {

        }
    }

    private fun buildSignInRequest(): BeginSignInRequest {
        return BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setFilterByAuthorizedAccounts(false)
                    .setServerClientId("320317569531-pjhcn24vdjomh7hne23v4lkdeqsq7rgj.apps.googleusercontent.com")
                    .build()
            )
            .setAutoSelectEnabled(true)
            .build()
    }
}*/








