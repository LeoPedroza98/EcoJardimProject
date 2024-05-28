package com.pedroza.infnet.ecojardimproject.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Projeto EcoJardimProject *Mobile*"

    }
    val text: LiveData<String> = _text
}