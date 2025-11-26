package com.example.habitstracker.app

import android.app.Application
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App: Application() {
    override fun onCreate() {
        super.onCreate()

       /* val settings = FirebaseFirestoreSettings.Builder()
            .setPersistenceEnabled(true)
            .build()

        FirebaseFirestore.getInstance().firestoreSettings = settings*/

    }
}