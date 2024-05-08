package com.pedroza.infnet.ecojardimproject.service

interface ClienteApiService {
    suspend fun GetClientes()
    suspend fun CreateClientes()
    suspend fun EditClientes()

    suspend fun DeleteClientes()
}