package com.pedroza.infnet.ecojardimproject.models

import java.util.Date

data class Orcamento(
    val id: Long,
    val nome: String,
    val dataCriacao: String,
    val descricao: String,
    val projetoId: Long,
    val projeto: Projeto?,
    val servicos: Collection<Servico>?
)
