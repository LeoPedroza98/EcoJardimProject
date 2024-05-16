package com.pedroza.infnet.ecojardimproject.service

import com.pedroza.infnet.ecojardimproject.models.Cliente
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


interface ClienteApiService {
    @GET("/clientes")
    fun getClientes(): Call<List<Cliente?>?>?

    @POST("/clientes")
    fun createCliente(@Body cliente: Cliente?): Call<Cliente?>?

    @DELETE("/clientes/{id}")
    fun deleteCliente(@Path("id") id: Long?): Call<Void?>?
}