package com.pedroza.infnet.ecojardimproject.models

data class Projeto(
    var id: Long,
    var nome: String,
    var descricao: String,
    var statusId: Long?,
    var status: Status?,
    var prazoInicial: String,
    var prazoFinal: String?,
    var valor: Double,
    var clienteId: Long,
    var cliente: Cliente?
)
