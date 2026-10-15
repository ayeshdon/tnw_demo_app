package com.tnw.demoapp.data.local

/**
 * Abstraction interface for local authentication preferences (DIP).
 */
interface AuthPreferencesRepository {
    var isAuthenticated: Boolean
    fun clear()
}
