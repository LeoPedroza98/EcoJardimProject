package com.pedroza.infnet.ecojardimproject.ui.projeto

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pedroza.infnet.ecojardimproject.models.Cliente
import com.pedroza.infnet.ecojardimproject.models.Status
import com.pedroza.infnet.ecojardimproject.service.RetrofitService
import kotlinx.coroutines.launch

class ProjetoFormViewModel : ViewModel() {
    val _statusList = MutableLiveData<List<Status>>()
    val statusList: LiveData<List<Status>> get() = _statusList
    val _clienteList = MutableLiveData<List<Cliente>>()
    val clienteList: LiveData<List<Cliente>> get() = _clienteList

    val nomeProjeto = MutableLiveData<String>()
    val descricao = MutableLiveData<String>()
    val dataInicial = MutableLiveData<String>()
    val dataFinal = MutableLiveData<String>()
    val valorEstimado = MutableLiveData<Double>()
    val statusSelecionado = MutableLiveData<Status>()
    val clienteSelecionado = MutableLiveData<Cliente>()
    val _saveButtonClickListener = MutableLiveData<Boolean>()
    val saveButtonClickListener: LiveData<Boolean> = _saveButtonClickListener


    fun OnSalvarButtonProjetos(nomeProjeto: String, descricao: String, dataInicial: String, dataFinal: String, valor: Double, statusId: Long, clienteId: Long){
        this.nomeProjeto.value = nomeProjeto
        this.descricao.value = descricao
        this.dataInicial.value = dataInicial
        this.dataFinal.value = dataFinal
        this.valorEstimado.value = valor
        this.statusSelecionado.value = _statusList.value?.firstOrNull { it.id == statusId }
        this.clienteSelecionado.value = _clienteList.value?.firstOrNull { it.id == clienteId }
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
                    Log.e("ProjetoFormViewModel", "Erro na resposta: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("ProjetoFormViewModel", "Erro na chamada de API", e)
            }
        }
    }

    fun getClienteFromApi(){
        viewModelScope.launch {
            try {
                val response = RetrofitService.clienteApiService.getClienteCoroutines()
                if (response.isSuccessful){
                    response.body()?.let { clienteResponse ->
                        _clienteList.value = clienteResponse.items
                    }
                }
                else {
                    Log.e("ProjetoFormViewModel", "Erro na resposta: ${response.errorBody()?.string()}")
                }
            }catch (e: Exception) {
                Log.e("ProjetoFormViewModel", "Erro na chamada de API", e)
            }
        }
    }
}