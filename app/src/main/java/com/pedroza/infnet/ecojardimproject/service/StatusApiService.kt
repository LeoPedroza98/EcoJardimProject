package com.pedroza.infnet.ecojardimproject.service

import com.pedroza.infnet.ecojardimproject.models.Status
import retrofit2.Response
import retrofit2.http.GET

interface StatusApiService {
    @GET("/status/listar")
    suspend fun getStatus(): Response<List<Status>>
}