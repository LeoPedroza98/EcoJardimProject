package com.pedroza.infnet.ecojardimproject.service

import com.pedroza.infnet.ecojardimproject.models.Cliente
import com.pedroza.infnet.ecojardimproject.response.ClienteResponse
import com.pedroza.infnet.ecojardimproject.response.StatusResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path


interface ClienteApiService {
    @GET("/Cliente")
    fun getClientes(): Call<ClienteResponse>
    @GET("/Cliente")
    suspend fun getClienteCoroutines(): Response<ClienteResponse>
    @POST("/Cliente")
    fun createCliente(@Body cliente: Cliente?): Call<Cliente?>?
    @PATCH("Cliente/{id}")
    fun updateCliente(@Path("id") id: Long, @Body operations: List<JsonPatchOperation>): Call<Cliente?>

    @DELETE("/Cliente/{id}")
    fun deleteCliente(@Path("id") id: Long?): Call<Void?>?
}