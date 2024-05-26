package com.pedroza.infnet.ecojardimproject.service

import com.pedroza.infnet.ecojardimproject.models.Cliente
import com.pedroza.infnet.ecojardimproject.models.Projeto
import com.pedroza.infnet.ecojardimproject.response.ClienteResponse
import com.pedroza.infnet.ecojardimproject.response.ProjetoResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface ProjetoApiService {
    @GET("/Projeto")
    fun getProjetos(): Call<ProjetoResponse>

    @POST("/Projeto")
    fun createProjeto(@Body projeto: Projeto?): Call<Projeto?>?

    @PATCH("Projeto/{id}")
    fun updateProjeto(@Path("id") id: Long, @Body operations: List<JsonPatchOperation>): Call<Projeto?>

    @DELETE("/Projeto/{id}")
    fun deleteProjeto(@Path("id") id: Long?): Call<Void?>?
}