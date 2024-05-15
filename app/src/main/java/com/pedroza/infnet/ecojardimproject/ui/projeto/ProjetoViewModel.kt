package com.pedroza.infnet.ecojardimproject.ui.projeto

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProjetoViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Projeto Fragment"
    }
    val text: LiveData<String> = _text
}