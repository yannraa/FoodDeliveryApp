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
            Log.d("OrderRepository", orderRequest.toString())

            val orderReq = OrderRequest(
                restaurant = 2,
                items = listOf(
                    OrderItem(
                        itemId = 1,
                        quantity = 1,
                        notes = "No onions"
                    )
                ),
                totalPrice = 10.0,
                user = 2,
                )
          //  Log.d("OrderRepository", "Request JSON: ${Gson().toJson(OrderDataRequest(orderReq))}")
            val response = apiService.createOrder(OrderDataRequest(orderReq))
            Result.success(response.data)
        } catch (e: Exception) {
            Log.d("OrderRepository", e.stackTraceToString())
            Result.failure(e)
        }
    }
}