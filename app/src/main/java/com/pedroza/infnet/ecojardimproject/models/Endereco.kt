package com.pedroza.infnet.ecojardimproject.models

data class Endereco(
    val logradouro: String,
    val numero: String,
    val complemento: String,
    val cep: String,
    val bairro: String,
    val municipio: String
)
