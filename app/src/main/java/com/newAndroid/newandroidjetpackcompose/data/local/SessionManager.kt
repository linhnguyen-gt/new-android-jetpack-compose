package com.newAndroid.newandroidjetpackcompose.data.local

import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionManager @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {

    fun clearSession() {
        // Clear all stored session data (e.g., token, user info)
        sharedPreferences.edit().clear().apply()
    }

    fun saveToken(token: String) {
        // Save the new token in SharedPreferences
        sharedPreferences.edit().putString("TOKEN", token).apply()
    }

    fun getToken(): String? {
        // Retrieve the saved token from SharedPreferences
        return sharedPreferences.getString("TOKEN", null)
    }
}
