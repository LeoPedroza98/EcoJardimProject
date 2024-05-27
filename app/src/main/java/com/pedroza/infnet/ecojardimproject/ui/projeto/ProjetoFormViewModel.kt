package com.pedroza.infnet.ecojardimproject.ui.projeto

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pedroza.infnet.ecojardimproject.models.Status
import com.pedroza.infnet.ecojardimproject.service.RetrofitService
import kotlinx.coroutines.launch

class ProjetoFormViewModel : ViewModel() {
    private val _statusList = MutableLiveData<List<Status>>()
    val statusList: LiveData<List<Status>> get() = _statusList


    fun getStatusFromApi() {
        viewModelScope.launch {
            try {
                val response = RetrofitService.statusApiService.getStatus()
                if (response.isSuccessful) {
                    _statusList.value = response.body()
                } else {
                }
            } catch (e: Exception) {
            }
        }
    }
}