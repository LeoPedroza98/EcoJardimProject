package com.pedroza.infnet.ecojardimproject.service.api.clientes

interface ClientesApiService {

    suspend fun GetClientes()
    suspend fun CreateClientes()
    suspend fun EditClientes()

    suspend fun DeleteClientes()
}