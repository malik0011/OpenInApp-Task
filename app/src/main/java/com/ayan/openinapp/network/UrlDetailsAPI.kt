package com.ayan.openinapp.network

import ApiResponse
import retrofit2.Response
import retrofit2.http.GET

interface UrlDetailsAPI {
    @GET("dashboardNew")
    suspend fun getAllData(): Response<ApiResponse>
}