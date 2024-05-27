package com.pedroza.infnet.ecojardimproject.ui.projeto

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pedroza.infnet.ecojardimproject.models.Cliente
import com.pedroza.infnet.ecojardimproject.models.Projeto
import com.pedroza.infnet.ecojardimproject.response.ClienteResponse
import com.pedroza.infnet.ecojardimproject.response.ProjetoResponse
import com.pedroza.infnet.ecojardimproject.service.ClienteApiService
import com.pedroza.infnet.ecojardimproject.service.ProjetoApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProjetoViewModel : ViewModel() {
    private val _projeto = MutableLiveData<List<Projeto>>()
    val projeto: LiveData<List<Projeto>> = _projeto

    private val _erro = MutableLiveData<String>()
    val erro: LiveData<String> = _erro

    fun carregarProjetos(projetoApiService: ProjetoApiService) {
        projetoApiService.getProjetos()?.enqueue(object : Callback<ProjetoResponse> {
            override fun onResponse(call: Call<ProjetoResponse>, response: Response<ProjetoResponse>) {
                if (response.isSuccessful) {
                    _projeto.value = response.body()?.items
                } else {
                    _erro.value = "Erro ao carregar projetos: ${response.message()}"
                }
            }

            override fun onFailure(call: Call<ProjetoResponse>, t: Throwable) {
                _erro.value = "Erro ao carregar projetos: ${t.message}"
            }
        })
    }

    fun excluirProjeto(projetoApiService: ProjetoApiService, id: Long) {
        projetoApiService.deleteProjeto(id)?.enqueue(object : Callback<Void?> {
            override fun onResponse(call: Call<Void?>, response: Response<Void?>) {
                if (response.isSuccessful) {
                    carregarProjetos(projetoApiService)
                } else {
                    _erro.value = "Erro ao excluir Projeto: ${response.message()}"
                }
            }

            override fun onFailure(call: Call<Void?>, t: Throwable) {
                _erro.value = "Erro ao excluir Projeto: ${t.message}"
            }
        })
    }
}