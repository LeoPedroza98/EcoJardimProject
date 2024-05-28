package com.pedroza.infnet.ecojardimproject.models

import java.io.Serializable
import java.util.Date

data class Orcamento(
    val id: Long,
    val nome: String,
    val dataCriacao: String,
    val descricao: String,
    val projetoId: Long?,
    val projeto: Projeto? = null,
    val servicos: Collection<Servico>?
): Serializable
