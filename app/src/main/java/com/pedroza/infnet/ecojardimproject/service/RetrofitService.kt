package com.pedroza.infnet.ecojardimproject.service

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitService {
    companion object {
        val BASE_URL = "http://192.168.1.13:5104"

        // Cria um interceptor que irá registrar o corpo das requisições e respostas
        private val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        // Adiciona o interceptor ao cliente HTTP
        private val httpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        // Cria a instância do Retrofit com o cliente HTTP personalizado
        val apiEcoJardimProject: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient) // Usa o cliente HTTP com o interceptor
            .addConverterFactory(GsonConverterFactory.create())
            .build()


        val statusApiService: StatusApiService = apiEcoJardimProject.create(StatusApiService::class.java)
        val clienteApiService: ClienteApiService = apiEcoJardimProject.create(ClienteApiService::class.java)
        val projetoApiService: ProjetoApiService = apiEcoJardimProject.create(ProjetoApiService::class.java)
        val orcamentoApiService: OrcamentoApiService = apiEcoJardimProject.create(OrcamentoApiService::class.java)
    }
}

