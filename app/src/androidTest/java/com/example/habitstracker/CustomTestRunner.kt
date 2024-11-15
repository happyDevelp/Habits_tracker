package com.example.habitstracker

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import dagger.hilt.android.testing.HiltTestApplication

class CustomTestRunner: AndroidJUnitRunner() {

    override fun newApplication(cl: ClassLoader?, className: String?, context: Context?)
    : Application {                     /*"dagger.hilt.android.testing.HiltTestApplication"*/
        return super.newApplication(cl, HiltTestApplication::class.java.name, context)
    }
}