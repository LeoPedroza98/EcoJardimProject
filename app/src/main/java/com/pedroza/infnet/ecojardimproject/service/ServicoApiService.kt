package com.pedroza.infnet.ecojardimproject.service

interface ServicoApiService {
    suspend fun GetServicos()
    suspend fun CreateServicos()
}