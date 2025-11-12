package com.example.habitstracker.me.presentation.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

@Composable
fun GoogleSignIn() {
    val context = LocalContext.current

    //Setting up login parameters
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
}