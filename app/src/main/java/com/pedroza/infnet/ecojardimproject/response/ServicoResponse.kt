package com.pedroza.infnet.ecojardimproject.response

import com.pedroza.infnet.ecojardimproject.models.Orcamento
import com.pedroza.infnet.ecojardimproject.models.Servico

data class ServicoResponse(
    val items: List<Servico>,
    val count: Int
)
