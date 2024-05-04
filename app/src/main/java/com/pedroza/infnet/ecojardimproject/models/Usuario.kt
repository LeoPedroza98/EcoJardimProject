package com.pedroza.infnet.ecojardimproject.models

import java.util.Date

data class Usuario (
    val Nome: String,
    val Sobrenome: String,
    val AcessoHabilitado: Boolean,
    val DataRegistro: Date
)