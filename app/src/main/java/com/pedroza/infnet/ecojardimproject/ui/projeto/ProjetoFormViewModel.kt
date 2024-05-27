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
    private val _statusList = MutableLiveData<List<Status>>()
    val statusList: LiveData<List<Status>> get() = _statusList

    private val _clienteList = MutableLiveData<List<Cliente>>()
    val clienteList: LiveData<List<Cliente>> get() = _clienteList


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