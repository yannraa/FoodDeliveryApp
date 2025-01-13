package com.example.tdm.data.network

import com.example.tdm.data.model.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("restaurants?populate=menu")
    suspend fun getRestaurants(): StrapiRestaurantResponse

    @POST("auth/local")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @POST("orders?populate[0]=restaurant")
    suspend fun createOrder(@Body orderRequest: OrderDataRequest): OrderDataResponse

    @GET("orders?populate[0]=restaurant")
    suspend fun getOrders(): AllOrdersDataResponse
}
