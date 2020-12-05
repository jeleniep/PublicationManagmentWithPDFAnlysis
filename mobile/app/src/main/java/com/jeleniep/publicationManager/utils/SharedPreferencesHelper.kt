package com.jeleniep.publicationManager.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

class SharedPreferencesHelper(context: Context) {

    private var sharedPreferences: SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(context)


    companion object {
        const val AUTH_TOKEN: String = "AUTH_TOKEN"
    }

    fun saveAuthToken(token: String) {
        var sharedPreferencesEditor: SharedPreferences.Editor = sharedPreferences.edit()
        sharedPreferencesEditor.putString(AUTH_TOKEN, token)
        sharedPreferencesEditor.apply()
    }

    fun getAuthToken(): String {
        return sharedPreferences.getString(AUTH_TOKEN, "")!!
    }
}