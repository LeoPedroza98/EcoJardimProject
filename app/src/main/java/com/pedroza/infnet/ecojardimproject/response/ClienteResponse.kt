package com.pedroza.infnet.ecojardimproject.response

import com.pedroza.infnet.ecojardimproject.models.Cliente

data class ClienteResponse(
    val items: List<Cliente>,
    val count: Int
)
