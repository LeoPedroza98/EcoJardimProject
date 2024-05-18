package com.pedroza.infnet.ecojardimproject.ui.cliente

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pedroza.infnet.ecojardimproject.models.Cliente
import com.pedroza.infnet.ecojardimproject.response.ClienteResponse
import com.pedroza.infnet.ecojardimproject.service.ClienteApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ClienteViewModel : ViewModel() {
    private val _clientes = MutableLiveData<List<Cliente>>()
    val clientes: LiveData<List<Cliente>> = _clientes

    private val _erro = MutableLiveData<String>()
    val erro: LiveData<String> = _erro

//    fun carregarClientes(clienteApiService: ClienteApiService) {
//        clienteApiService.getClientes()?.enqueue(object : Callback<List<Cliente?>?> {
//            override fun onResponse(call: Call<List<Cliente?>?>, response: Response<List<Cliente?>?>) {
//                if (response.isSuccessful) {
//                    _clientes.value = response.body() as List<Cliente>?
//                } else {
//                    _erro.value = "Erro ao carregar clientes: ${response.message()}"
//                }
//            }
//
//            override fun onFailure(call: Call<List<Cliente?>?>, t: Throwable) {
//                _erro.value = "Erro ao carregar clientes: ${t.message}"
//            }
//        })
//    }
    fun carregarClientes(clienteApiService: ClienteApiService) {
        clienteApiService.getClientes()?.enqueue(object : Callback<ClienteResponse> {
            override fun onResponse(call: Call<ClienteResponse>, response: Response<ClienteResponse>) {
                if (response.isSuccessful) {
                    // Aqui você precisa extrair a lista de clientes do objeto ClienteResponse
                    _clientes.value = response.body()?.items
                } else {
                    _erro.value = "Erro ao carregar clientes: ${response.message()}"
                }
            }

            override fun onFailure(call: Call<ClienteResponse>, t: Throwable) {
                _erro.value = "Erro ao carregar clientes: ${t.message}"
            }
        })
    }


    fun criarCliente(clienteApiService: ClienteApiService, cliente: Cliente) {
        clienteApiService.createCliente(cliente)?.enqueue(object : Callback<Cliente?> {
            override fun onResponse(call: Call<Cliente?>, response: Response<Cliente?>) {
                if (response.isSuccessful) {
                    carregarClientes(clienteApiService)
                } else {
                    _erro.value = "Erro ao criar cliente: ${response.message()}"
                }
            }

            override fun onFailure(call: Call<Cliente?>, t: Throwable) {
                _erro.value = "Erro ao criar cliente: ${t.message}"
            }
        })
    }

    fun excluirCliente(clienteApiService: ClienteApiService, id: Long) {
        clienteApiService.deleteCliente(id)?.enqueue(object : Callback<Void?> {
            override fun onResponse(call: Call<Void?>, response: Response<Void?>) {
                if (response.isSuccessful) {
                    carregarClientes(clienteApiService)
                } else {
                    _erro.value = "Erro ao excluir cliente: ${response.message()}"
                }
            }

            override fun onFailure(call: Call<Void?>, t: Throwable) {
                _erro.value = "Erro ao excluir cliente: ${t.message}"
            }
        })
    }
}