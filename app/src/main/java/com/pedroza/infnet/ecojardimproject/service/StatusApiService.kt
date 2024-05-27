package com.pedroza.infnet.ecojardimproject.service

import com.pedroza.infnet.ecojardimproject.models.Status
import com.pedroza.infnet.ecojardimproject.response.StatusResponse
import retrofit2.Response
import retrofit2.http.GET

interface StatusApiService {
    @GET("/status/listar")
    suspend fun getStatus(): Response<StatusResponse>


}