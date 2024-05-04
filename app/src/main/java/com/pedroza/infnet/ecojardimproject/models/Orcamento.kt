package com.pedroza.infnet.ecojardimproject.models

import java.time.LocalDateTime
import java.util.Date

data class Orcamento(
    val id: Long,
    val nome: String,
    val dataCriacao: Date,
    val descricao: String,
    val projetoId: Long,
    val projeto: Projeto?,
    val servicos: Collection<Servico>?
)
