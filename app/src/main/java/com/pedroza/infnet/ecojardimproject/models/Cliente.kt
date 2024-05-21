package com.pedroza.infnet.ecojardimproject.models

import java.io.Serializable

data class Cliente(
    var id: Long,
    var nome: String,
    var sobrenome: String,
    var documento: String,
    var endereco: Endereco?,
    var contato: Contato?,
    var projetos: List<Projeto> = listOf()
): Serializable
