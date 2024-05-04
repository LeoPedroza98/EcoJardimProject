package com.pedroza.infnet.ecojardimproject.models

data class Status(
    val id: Long,
    val nome: String
) {
    companion object {
        val EmAndamento = Status(1, "Em Andamento")
        val EmEspera = Status(2, "Em Espera")
        val AguardandoAprovacao = Status(3, "Aguardando Aprovação")
        val Concluido = Status(4, "Concluído")
        val Cancelado = Status(5, "Cancelado")

        fun obterDados(): Array<Status> {
            return arrayOf(
                EmAndamento,
                EmEspera,
                AguardandoAprovacao,
                Concluido,
                Cancelado
            )
        }
    }
}
