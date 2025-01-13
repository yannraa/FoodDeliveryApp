package com.example.tdm.data.repository
import com.example.tdm.data.network.ApiService
import com.example.tdm.data.model.LoginRequest
import com.example.tdm.data.model.LoginResponse
import com.example.tdm.data.model.SignUpRequest
import com.example.tdm.data.model.SignUpResponse
import retrofit2.HttpException
import java.io.IOException

class AuthRepository(private val api: ApiService) {

    // Méthode pour la connexion
    suspend fun login(identifier: String, password: String): Result<LoginResponse> {
        return try {
            val response = api.login(LoginRequest(identifier, password))
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Méthode pour l'inscription
    suspend fun signUp(signUpRequest: SignUpRequest): Result<SignUpResponse> {
        return try {
            val response = api.signUp(signUpRequest)
            Result.success(response)

        } catch (e: IOException) {
            Result.failure(e)
        } catch (e: HttpException) {
            Result.failure(e)
        }
    }
}