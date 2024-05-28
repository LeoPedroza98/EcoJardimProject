package com.pedroza.infnet.ecojardimproject.service

import com.pedroza.infnet.ecojardimproject.models.Orcamento
import com.pedroza.infnet.ecojardimproject.models.Servico
import com.pedroza.infnet.ecojardimproject.response.OrcamentoResponse
import com.pedroza.infnet.ecojardimproject.response.ServicoResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface ServicoApiService {
    @GET("/Servico")
    @Headers("include: Orcamento,Status")
    fun getServicos(): Call<ServicoResponse>
    @POST("/Servico")
    fun createServico(@Body servico: Servico?): Call<Servico?>?

    @PATCH("Servico/{id}")
    fun updateServico(@Path("id") id: Long, @Body operations: List<JsonPatchOperation>): Call<Servico?>

    @DELETE("/Servico/{id}")
    fun deleteServico(@Path("id") id: Long?): Call<Void?>?
}