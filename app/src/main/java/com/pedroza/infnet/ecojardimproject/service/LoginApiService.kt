package com.pedroza.infnet.ecojardimproject.service

import com.pedroza.infnet.ecojardimproject.models.Usuario
import com.pedroza.infnet.ecojardimproject.models.UsuarioToken
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApiService {
    @POST("login")
    fun login(@Body usuario: Usuario): Call<UsuarioToken>
}