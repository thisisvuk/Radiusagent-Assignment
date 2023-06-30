package com.example.assignmentradiusagent.model

import com.example.assignmentradiusagent.model.dataclass.ApiResponse
import retrofit2.http.GET

interface ApiService {
    @GET("db")
    suspend fun getApiData(): ApiResponse
}
