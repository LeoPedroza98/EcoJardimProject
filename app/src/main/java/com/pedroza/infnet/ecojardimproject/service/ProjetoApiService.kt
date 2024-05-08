package com.pedroza.infnet.ecojardimproject.service

interface ProjetoApiService {
    suspend fun GetProjetos()

    suspend fun CreateProjeto()

    suspend fun DeleteProjeto()
}