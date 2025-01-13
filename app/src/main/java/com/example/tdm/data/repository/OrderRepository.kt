package com.example.tdm.data.repository
import android.util.Log
import com.example.tdm.data.model.OrderDataRequest
import com.example.tdm.data.model.OrderItem
import com.example.tdm.data.model.OrderRequest
import com.example.tdm.data.model.OrderResponse
import com.example.tdm.data.network.ApiService
import com.google.gson.Gson

class OrderRepository(private val apiService: ApiService) {

    suspend fun createOrder(orderRequest: OrderRequest): Result<OrderResponse> {
        return try {
            Log.d("OrderRepository", "Request JSON: ${Gson().toJson(OrderDataRequest(orderRequest))}")
            val response = apiService.createOrder(OrderDataRequest(orderRequest))
            Result.success(response.data)
        } catch (e: Exception) {
            Log.d("OrderRepository", e.stackTraceToString())
            Result.failure(e)
        }
    }

    suspend fun getOrders(userId: Int): List<OrderResponse> {
        return try {
            val response = apiService.getOrders() // Requête API
            response.data // Retourne la liste des commandes depuis l'API
        } catch (e: Exception) {
            throw e // Gère les erreurs en remontant l'exception
        }
    }
}