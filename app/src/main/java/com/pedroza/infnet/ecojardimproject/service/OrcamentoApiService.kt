package com.pedroza.infnet.ecojardimproject.service

interface OrcamentoApiService {
    suspend fun GetOrcamentos()
    suspend fun CreateOrcamentos()
}