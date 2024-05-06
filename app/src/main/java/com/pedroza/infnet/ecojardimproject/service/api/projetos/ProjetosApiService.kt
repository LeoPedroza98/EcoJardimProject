package com.pedroza.infnet.ecojardimproject.service.api.projetos

interface ProjetosApiService {

    suspend fun GetProjetos()

    suspend fun CreateProjeto()

    suspend fun DeleteProjeto()
}