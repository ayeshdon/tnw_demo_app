package com.tnw.demoapp.data.remote

interface AuthApiService {
    suspend fun authenticate(username: String, password: String): Boolean
}