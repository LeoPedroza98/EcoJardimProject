package com.pedroza.infnet.ecojardimproject.service

import com.pedroza.infnet.ecojardimproject.models.Orcamento
import com.pedroza.infnet.ecojardimproject.models.Projeto
import com.pedroza.infnet.ecojardimproject.response.ClienteResponse
import com.pedroza.infnet.ecojardimproject.response.OrcamentoResponse
import com.pedroza.infnet.ecojardimproject.response.ProjetoResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface OrcamentoApiService {
    @GET("/Orcamento")
    @Headers("include: Projeto")
    fun getOrcamentos(): Call<OrcamentoResponse>

    @GET("/Orcamento")
    suspend fun getOrcamentoCoroutines(): Response<OrcamentoResponse>
    @POST("/Orcamento")
    fun createOrcamento(@Body orcamento: Orcamento?): Call<Orcamento?>?
    @PATCH("Orcamento/{id}")
    fun updateOrcamento(@Path("id") id: Long, @Body operations: List<JsonPatchOperation>): Call<Orcamento?>
    @DELETE("/Orcamento/{id}")
    fun deleteOrcamento(@Path("id") id: Long?): Call<Void?>?
}