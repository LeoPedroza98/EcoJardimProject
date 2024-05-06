package com.pedroza.infnet.ecojardimproject.service.api.orcamentos

interface OrcamentosApiService {

    suspend fun GetOrcamentos()
    suspend fun CreateOrcamentos()
}