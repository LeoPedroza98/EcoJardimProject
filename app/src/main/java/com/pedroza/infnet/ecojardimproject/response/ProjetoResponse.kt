package com.pedroza.infnet.ecojardimproject.response

import com.pedroza.infnet.ecojardimproject.models.Cliente
import com.pedroza.infnet.ecojardimproject.models.Projeto

data class ProjetoResponse (
    val items: List<Projeto>,
    val count: Int
)