package com.pedroza.infnet.ecojardimproject.response

import com.pedroza.infnet.ecojardimproject.models.Orcamento

data class OrcamentoResponse (
    val items: List<Orcamento>,
    val count: Int
)