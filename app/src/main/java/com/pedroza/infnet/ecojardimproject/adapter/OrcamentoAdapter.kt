package com.pedroza.infnet.ecojardimproject.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.pedroza.infnet.ecojardimproject.R
import com.pedroza.infnet.ecojardimproject.models.Orcamento
import com.pedroza.infnet.ecojardimproject.models.Projeto
import com.pedroza.infnet.ecojardimproject.service.OrcamentoApiService
import com.pedroza.infnet.ecojardimproject.ui.orcamentos.OrcamentosViewModel

class OrcamentoAdapter(
    private var listaOrcamentos: List<Orcamento>,
    private val orcamentoViewModel: OrcamentosViewModel,
    private val orcamentoApiService: OrcamentoApiService
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

    fun excluirOrcamento(position: Int) {
        if (listaOrcamentos.isNotEmpty()) {
            val orcamentoExcluido = listaOrcamentos[position]
            listaOrcamentos = listaOrcamentos.filterIndexed { index, _ -> index != position }
            notifyDataSetChanged()
            orcamentoViewModel.excluirProjeto(orcamentoApiService, orcamentoExcluido.id)
        }
    }

    override fun getItemCount(): Int {
        return if (listaOrcamentos.isEmpty()) 1 else listaOrcamentos.size
    }

    override fun onBindViewHolder(holder: OrcamentoViewHolder, position: Int) {
        if (listaOrcamentos.isEmpty()) {
            holder.adicionarOrcamento.visibility = View.VISIBLE

            holder.adicionarOrcamento.setOnClickListener {
                holder.itemView.findNavController()
                    .navigate(R.id.action_nav_orcamentos_to_orcamentosFormFragment)
            }
            // Limpa os campos de texto
            holder.nomeOrcamento.text = ""
            holder.descricaoOrcamento.text = ""
            holder.projetoOrcamento.text = ""
        } else {
            val orcamentos = listaOrcamentos[position]
            holder.nomeOrcamento.visibility = View.VISIBLE
            holder.descricaoOrcamento.visibility = View.VISIBLE
            holder.projetoOrcamento.visibility = View.VISIBLE
            holder.editarOrcamento.visibility = View.VISIBLE
            holder.excluirOrcamento.visibility = View.VISIBLE
            holder.adicionarOrcamento.visibility = View.GONE

            holder.nomeOrcamento.text = orcamentos.nome
            holder.descricaoOrcamento.text = orcamentos.descricao
            holder.projetoOrcamento.text = orcamentos.projeto?.nome

            if (position == 0) {
                holder.adicionarOrcamento.visibility = View.VISIBLE
                holder.adicionarOrcamento.setOnClickListener {
                    holder.itemView.findNavController()
                        .navigate(R.id.action_nav_orcamentos_to_orcamentosFormFragment)
                }
            } else {
                holder.adicionarOrcamento.visibility = View.GONE
            }

            holder.editarOrcamento.setOnClickListener {
                val bundle = Bundle().apply {
                    putSerializable("orcamento", orcamentos)
                }
                holder.itemView.findNavController()
                    .navigate(R.id.action_nav_orcamentos_to_orcamentosFormFragment, bundle)
            }

            holder.excluirOrcamento.setOnClickListener {
                excluirOrcamento(position)
            }
        }
    }
}