package com.tnw.demoapp.data.repository

import com.tnw.demoapp.data.remote.AuthApiService
import com.tnw.demoapp.domain.repository.AuthenticationRepository

open class AuthenticationRepositoryImpl(
    private val apiService: AuthApiService
) : AuthenticationRepository {
    override suspend fun authenticate(username: String, password: String): Boolean =
        apiService.authenticate(username, password)
}
