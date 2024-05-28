package com.pedroza.infnet.ecojardimproject.ui.orcamentos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pedroza.infnet.ecojardimproject.models.Orcamento
import com.pedroza.infnet.ecojardimproject.response.OrcamentoResponse
import com.pedroza.infnet.ecojardimproject.service.OrcamentoApiService
import com.pedroza.infnet.ecojardimproject.service.ProjetoApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrcamentosViewModel : ViewModel() {
    private val _orcamento = MutableLiveData<List<Orcamento>>()
    val orcamento: LiveData<List<Orcamento>> = _orcamento

    private val _erro = MutableLiveData<String>()
    val erro: LiveData<String> = _erro


    fun carregarOrcamentos(orcamentoApiService: OrcamentoApiService) {
        orcamentoApiService.getOrcamentos()?.enqueue(object : Callback<OrcamentoResponse> {
            override fun onResponse(call: Call<OrcamentoResponse>, response: Response<OrcamentoResponse>) {
                if (response.isSuccessful) {
                    _orcamento.value = response.body()?.items
                } else {
                    _erro.value = "Erro ao carregar Orçamentos: ${response.message()}"
                }
            }

            override fun onFailure(call: Call<OrcamentoResponse>, t: Throwable) {
                _erro.value = "Erro ao carregar Orçamentos: ${t.message}"
            }
        })
    }

    fun excluirProjeto(orcamentoApiService: OrcamentoApiService, id: Long) {
        orcamentoApiService.deleteOrcamento(id)?.enqueue(object : Callback<Void?> {
            override fun onResponse(call: Call<Void?>, response: Response<Void?>) {
                if (response.isSuccessful) {
                    carregarOrcamentos(orcamentoApiService)
                } else {
                    _erro.value = "Erro ao excluir Orçamento: ${response.message()}"
                }
            }

            override fun onFailure(call: Call<Void?>, t: Throwable) {
                _erro.value = "Erro ao excluir Orçamento: ${t.message}"
            }
        })
    }
}