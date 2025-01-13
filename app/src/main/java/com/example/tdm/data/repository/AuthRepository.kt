package com.example.tdm.data.repository

import com.example.tdm.data.network.ApiService
import com.example.tdm.data.model.LoginRequest
import com.example.tdm.data.model.LoginResponse

class AuthRepository(private val api: ApiService) {

    suspend fun login(identifier: String, password: String): Result<LoginResponse> {
        return try {
            val response = api.login(LoginRequest(identifier, password))
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
