package com.pedroza.infnet.ecojardimproject.ui.servicos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pedroza.infnet.ecojardimproject.models.Servico
import com.pedroza.infnet.ecojardimproject.response.ServicoResponse
import com.pedroza.infnet.ecojardimproject.service.ServicoApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ServicosViewModel : ViewModel() {
    private val _servico = MutableLiveData<List<Servico>>()
    val servico: LiveData<List<Servico>> = _servico

    private val _erro = MutableLiveData<String>()
    val erro: LiveData<String> = _erro

    fun carregarServicos(servicoApiService: ServicoApiService) {
        servicoApiService.getServicos()?.enqueue(object : Callback<ServicoResponse> {
            override fun onResponse(call: Call<ServicoResponse>, response: Response<ServicoResponse>) {
                if (response.isSuccessful) {
                    _servico.value = response.body()?.items
                } else {
                    _erro.value = "Erro ao carregar Serviços: ${response.message()}"
                }
            }

            override fun onFailure(call: Call<ServicoResponse>, t: Throwable) {
                _erro.value = "Erro ao carregar Serviços: ${t.message}"
            }
        })
    }

    fun excluirServico(servicoApiService: ServicoApiService, id: Long) {
        servicoApiService.deleteServico(id)?.enqueue(object : Callback<Void?> {
            override fun onResponse(call: Call<Void?>, response: Response<Void?>) {
                if (response.isSuccessful) {
                    carregarServicos(servicoApiService)
                } else {
                    _erro.value = "Erro ao excluir Serviço: ${response.message()}"
                }
            }

            override fun onFailure(call: Call<Void?>, t: Throwable) {
                _erro.value = "Erro ao excluir Serviço: ${t.message}"
            }
        })
    }
}