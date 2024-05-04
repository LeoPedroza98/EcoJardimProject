package com.pedroza.infnet.ecojardimproject.models

data class Servico(
    val id: Long,
    val nome: String,
    val descricao: String,
    val valor: Double,
    val statusId: Long,
    val status: Status?,
    val orcamentoId: Long,
    val orcamento: Orcamento?,
    val dataInicio: String?,
    val dataFinalizacao: String?
)
