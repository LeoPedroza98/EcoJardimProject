package com.pedroza.infnet.ecojardimproject.ui.cliente

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ClienteFormViewModel : ViewModel() {
    val nome = MutableLiveData<String>()
    val sobrenome = MutableLiveData<String>()
    val documento = MutableLiveData<String>()

    val _saveButtonClickListener = MutableLiveData<Boolean>()
    val saveButtonClickListener: LiveData<Boolean> = _saveButtonClickListener

    fun onSalvarButtonClicked(nome: String, sobrenome: String, documento: String) {
        this.nome.value = nome
        this.sobrenome.value = sobrenome
        this.documento.value = documento
        _saveButtonClickListener.value = true
    }

    fun resetSaveButtonClickListener() {
        _saveButtonClickListener.value = false
    }
}
