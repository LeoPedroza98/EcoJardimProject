package com.pedroza.infnet.ecojardimproject.ui.orcamentos

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pedroza.infnet.ecojardimproject.models.Cliente
import com.pedroza.infnet.ecojardimproject.models.Projeto
import com.pedroza.infnet.ecojardimproject.models.Status
import com.pedroza.infnet.ecojardimproject.service.RetrofitService
import kotlinx.coroutines.launch

class OrcamentosFormViewModel : ViewModel() {
    val _projetoList = MutableLiveData<List<Projeto>>()
    val projetoList: LiveData<List<Projeto>> get() = _projetoList
    val nomeServico = MutableLiveData<String>()
    val descricaoServico = MutableLiveData<String>()
    val projetoSelecionado = MutableLiveData<Projeto>()
    val _saveButtonClickListener = MutableLiveData<Boolean>()
    val saveButtonClickListener: LiveData<Boolean> = _saveButtonClickListener


    fun OnSalvarButtonOrcamentos(nomeServico: String,descricao: String,projetoId: Long){
        this.nomeServico.value = nomeServico
        this.descricaoServico.value = descricao
        this.projetoSelecionado.value = _projetoList.value?.firstOrNull { it.id == projetoId }
        _saveButtonClickListener.value = true
    }
    fun getProjetoFromApi() {
        viewModelScope.launch {
            try {
                val response = RetrofitService.projetoApiService.getProjetoCoroutines()
                if (response.isSuccessful) {
                    response.body()?.let { projetoResponse ->
                        _projetoList.value = projetoResponse.items
                    }
                } else {
                    Log.e("OrcamentoFormViewModel", "Erro na resposta: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("OrcamentoFormViewModel", "Erro na chamada de API", e)
            }
        }
    }
}