package com.pedroza.infnet.ecojardimproject.models

class UsuarioToken {
    var autenticado: Boolean = false
    var dataCriacao: String? = null
    var dataExpiracao: String? = null
    var tokenDeAcesso: String? = null
    var mensagem: String? = null
}