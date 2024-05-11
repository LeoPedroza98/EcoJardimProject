package com.pedroza.infnet.ecojardimproject.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitService {
    companion object{
        val apiEcoJardimProject = Retrofit.Builder()
            .baseUrl("http://localhost:5104/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}