package com.pedroza.infnet.ecojardimproject.models

import java.io.Serializable

data class Projeto(
    var id: Long,
    var nome: String,
    var descricao: String,
    var statusId: Long?,
    var status: Status? = null,
    var prazoInicial: String,
    var prazoFinal: String,
    var valor: Double,
    var clienteId: Long?,
    var cliente: Cliente? = null
): Serializable
