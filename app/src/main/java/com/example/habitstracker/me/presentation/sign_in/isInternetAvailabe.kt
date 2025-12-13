package com.example.habitstracker.me.presentation.sign_in

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

fun Context.isInternetAvailable(): Boolean {
    val connectivityManager =
        getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    val activeNetwork = connectivityManager.activeNetwork ?: return false

    val capabilities =
        connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false

    return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
}