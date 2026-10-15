package com.tnw.demoapp.data.remote

/**
 * Interface defining the API contract for authentication services.When a real REST
 * API is implemented (e.g., via Retrofit or Ktor),
 * implement this interface and inject it into [com.tnw.demoapp.data.AuthenticationRepositoryImpl].
 */
interface AuthApiService {
    suspend fun authenticate(username: String, password: String): Boolean
}