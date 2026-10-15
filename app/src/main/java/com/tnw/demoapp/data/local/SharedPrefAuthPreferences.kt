package com.tnw.demoapp.data.local

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

/**
 * SharedPreferences implementation of [AuthPreferencesRepository].
 */
class SharedPrefAuthPreferences(context: Context) : AuthPreferencesRepository {
    private val prefs: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    override var isAuthenticated: Boolean
        get() = prefs.getBoolean(KEY_IS_AUTHENTICATED, false)
        set(value) {
            prefs.edit { putBoolean(KEY_IS_AUTHENTICATED, value) }
        }

    override fun clear() {
        prefs.edit { remove(KEY_IS_AUTHENTICATED) }
    }

    private companion object {
        const val PREFS_NAME = "auth_preferences"
        const val KEY_IS_AUTHENTICATED = "is_authenticated"
    }
}
