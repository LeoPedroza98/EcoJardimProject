package com.pedroza.infnet.ecojardimproject.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pedroza.infnet.ecojardimproject.R
import com.pedroza.infnet.ecojardimproject.models.Projeto
import com.pedroza.infnet.ecojardimproject.models.Servico
import com.pedroza.infnet.ecojardimproject.service.ProjetoApiService
import com.pedroza.infnet.ecojardimproject.service.ServicoApiService
import com.pedroza.infnet.ecojardimproject.ui.projeto.ProjetoViewModel
import com.pedroza.infnet.ecojardimproject.ui.servicos.ServicosViewModel

class ServicosAdapter(
    private var listaServico: List<Servico>,
    private val servicoViewModel: ServicosViewModel,
    private val servicoApiService: ServicoApiService
): RecyclerView.Adapter<ServicosAdapter.ServicoViewHolder>() {
    class ServicoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nomeServico: TextView = itemView.findViewById(R.id.item_nome_servico)
        val descricaoServico: TextView = itemView.findViewById(R.id.item_descricao_servico)
        val valorServico: TextView = itemView.findViewById(R.id.item_valor_servico)
        val statusServico: TextView = itemView.findViewById(R.id.item_status_servico)
        val btnAdicionaServico: Button = itemView.findViewById(R.id.input_adicionar_servicos)
        val btnEditaServico: ImageButton = itemView.findViewById(R.id.edit_servico)
        val btnExcluiServico: ImageButton = itemView.findViewById(R.id.excluir_servico)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServicoViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.item_servicos, parent, false)
        return ServicosAdapter.ServicoViewHolder(itemView)
    }

    override fun getItemCount(): Int {
       return listaServico.size
    }

    fun updateServicoList(newList: List<Servico>) {
        listaServico = newList
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ServicoViewHolder, position: Int) {
        val servico = listaServico[position]
        holder.nomeServico.text = servico.nome
        holder.descricaoServico.text = servico.descricao
        holder.valorServico.text = servico.valor.toString()
        holder.statusServico.text = servico.status?.nome

        if (position == 0) {
            holder.btnAdicionaServico.visibility = View.VISIBLE
        } else {
            holder.btnAdicionaServico.visibility = View.GONE
        }
    }
}