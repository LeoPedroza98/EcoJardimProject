package com.pedroza.infnet.ecojardimproject.ui.servicos

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pedroza.infnet.ecojardimproject.models.Orcamento
import com.pedroza.infnet.ecojardimproject.models.Status
import com.pedroza.infnet.ecojardimproject.service.RetrofitService
import kotlinx.coroutines.launch

class ServicosFormViewModel : ViewModel() {
    val _statusList = MutableLiveData<List<Status>>()
    val statusList: LiveData<List<Status>> get() = _statusList
    val _orcamentoList = MutableLiveData<List<Orcamento>>()
    val orcamentoList: LiveData<List<Orcamento>> get() = _orcamentoList
    val nomeServico = MutableLiveData<String>()
    val descricaoServico = MutableLiveData<String>()
    val dataInicio = MutableLiveData<String>()
    val dataFinalizacao = MutableLiveData<String>()
    val valorServico = MutableLiveData<Double>()
    val statusSelecionado = MutableLiveData<Status>()
    val OrcamentoSelecionado = MutableLiveData<Orcamento>()
    val _saveButtonClickListener = MutableLiveData<Boolean>()
    val saveButtonClickListener: LiveData<Boolean> = _saveButtonClickListener

    fun OnSalvarButtonServicos(nomeServico: String, descricao: String, dataInicio: String, dataFinal: String, valor: Double, statusId: Long, orcamentoId: Long){
        this.nomeServico.value = nomeServico
        this.descricaoServico.value = descricao
        this.dataInicio.value = dataInicio
        this.dataFinalizacao.value = dataFinal
        this.valorServico.value = valor
        this.statusSelecionado.value = _statusList.value?.firstOrNull { it.id == statusId }
        this.OrcamentoSelecionado.value = _orcamentoList.value?.firstOrNull { it.id == orcamentoId }
        _saveButtonClickListener.value = true
    }
    fun getStatusFromApi() {
        viewModelScope.launch {
            try {
                val response = RetrofitService.statusApiService.getStatus()
                if (response.isSuccessful) {
                    response.body()?.let { statusResponse ->
                        _statusList.value = statusResponse.items
                    }
                } else {
                    Log.e("OrcamentoFormViewModel", "Erro na resposta: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("OrcamentoFormViewModel", "Erro na chamada de API", e)
            }
        }
    }

    fun getOrcamentosFromApi(){
        viewModelScope.launch {
            try {
                val response = RetrofitService.orcamentoApiService.getOrcamentoCoroutines()
                if (response.isSuccessful){
                    response.body()?.let { orcamentoResponse ->
                        _orcamentoList.value = orcamentoResponse.items
                    }
                }
                else {
                    Log.e("OrcamentoFormViewModel", "Erro na resposta: ${response.errorBody()?.string()}")
                }
            }catch (e: Exception) {
                Log.e("OrcamentoFormViewModel", "Erro na chamada de API", e)
            }
        }
    }
}