package com.tnw.demoapp.data.mockapis

import com.tnw.demoapp.data.remote.AuthApiService
import kotlinx.coroutines.delay

/**
 * Mock implementation of AuthApiService used for development and testing
 * prior to REST API integration.
 */
class MockAuthApiService : AuthApiService {
    override suspend fun authenticate(username: String, password: String): Boolean =
        delay(1000).let {
            username.trim().equals(DEMO_USERNAME, ignoreCase = true) &&
                    password == DEMO_PASSWORD
        }

    private companion object {
        const val DEMO_USERNAME = "demo@example.com"
        const val DEMO_PASSWORD = "password123"
    }
}
