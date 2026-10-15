package com.tnw.demoapp.data.repository

import com.tnw.demoapp.data.remote.AuthApiService
import com.tnw.demoapp.domain.repository.AuthenticationRepository

/**
 * Production implementation of [AuthenticationRepository] that delegates API calls to an [AuthApiService].
 *
 * To switch to a real REST API when available, pass the REST API implementation of [AuthApiService]
 * into this repository. No changes to this repository class will be needed.
 */
open class AuthenticationRepositoryImpl(
    private val apiService: AuthApiService
) : AuthenticationRepository {
    override suspend fun authenticate(username: String, password: String): Boolean =
        apiService.authenticate(username, password)
}
