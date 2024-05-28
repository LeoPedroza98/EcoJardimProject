package com.pedroza.infnet.ecojardimproject.models

import java.io.Serializable

data class Servico(
    val id: Long,
    val nome: String,
    val descricao: String,
    val valor: Double,
    val statusId: Long?,
    val status: Status? = null,
    val orcamentoId: Long?,
    val orcamento: Orcamento? = null,
    val dataInicio: String?,
    val dataFinalizacao: String?
): Serializable
