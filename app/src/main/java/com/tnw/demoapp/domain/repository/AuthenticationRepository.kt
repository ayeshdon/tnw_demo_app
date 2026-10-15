package com.tnw.demoapp.domain.repository

interface AuthenticationRepository {
    suspend fun authenticate(username: String, password: String): Boolean
}
