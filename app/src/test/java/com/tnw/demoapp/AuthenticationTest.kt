package com.tnw.demoapp

import com.tnw.demoapp.data.repository.AuthenticationRepositoryImpl
import com.tnw.demoapp.data.mockapis.MockAuthApiService
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class AuthenticationTest {
    private val apiService = MockAuthApiService()
    private val repository = AuthenticationRepositoryImpl(apiService)

    @Test
    fun acceptsDemoCredentials() = runBlocking {
        assertTrue(repository.authenticate("demo@example.com", "password123"))
    }

    @Test
    fun rejectsInvalidCredentials() = runBlocking {
        assertFalse(repository.authenticate("demo@example.com", "wrong-password"))
        assertFalse(repository.authenticate("unknown@example.com", "password123"))
    }

}
