package com.pedroza.infnet.ecojardimproject.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pedroza.infnet.ecojardimproject.R
import com.pedroza.infnet.ecojardimproject.models.Orcamento
import com.pedroza.infnet.ecojardimproject.models.Projeto
import com.pedroza.infnet.ecojardimproject.service.OrcamentoApiService
import com.pedroza.infnet.ecojardimproject.ui.orcamentos.OrcamentosViewModel

class OrcamentoAdapter(
    private var listaOrcamentos: List<Orcamento>,
    private val OrcamentoViewModel: OrcamentosViewModel,
    private val OrcamentoApiService: OrcamentoApiService
) : RecyclerView.Adapter<OrcamentoAdapter.OrcamentoViewHolder>(){
    class OrcamentoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nomeOrcamento: TextView = itemView.findViewById(R.id.item_nome_orcamento)
        val descricaoOrcamento: TextView = itemView.findViewById(R.id.item_descricao_orcamento)
        val projetoOrcamento: TextView = itemView.findViewById(R.id.item_projeto_orcamento)
        val adicionarOrcamento: Button = itemView.findViewById(R.id.input_adicionar_orcamento)
        val editarOrcamento: ImageButton = itemView.findViewById(R.id.edit_orcamento)
        val excluirOrcamento: ImageButton = itemView.findViewById(R.id.excluir_orcamento)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrcamentoViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.item_orcamentos, parent, false)
        return OrcamentoViewHolder(itemView)
    }

    fun updateOrcamentoList(newList: List<Orcamento>) {
        listaOrcamentos = newList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return listaOrcamentos.size
    }

    override fun onBindViewHolder(holder: OrcamentoViewHolder, position: Int) {
        val orcamentos = listaOrcamentos[position]
        holder.nomeOrcamento.text = orcamentos.nome
        holder.descricaoOrcamento.text = orcamentos.descricao
        holder.projetoOrcamento.text = orcamentos.projeto?.nome

        if (position == 0) {
            holder.adicionarOrcamento.visibility = View.VISIBLE
        } else {
            holder.adicionarOrcamento.visibility = View.GONE
        }
    }

}