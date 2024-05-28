package com.pedroza.infnet.ecojardimproject.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.pedroza.infnet.ecojardimproject.R
import com.pedroza.infnet.ecojardimproject.models.Cliente
import com.pedroza.infnet.ecojardimproject.models.Projeto
import com.pedroza.infnet.ecojardimproject.models.Status
import com.pedroza.infnet.ecojardimproject.service.ClienteApiService
import com.pedroza.infnet.ecojardimproject.service.ProjetoApiService
import com.pedroza.infnet.ecojardimproject.ui.cliente.ClienteViewModel
import com.pedroza.infnet.ecojardimproject.ui.projeto.ProjetoViewModel
import org.threeten.bp.LocalDate
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.format.DateTimeFormatter

class ProjetoAdapter(
    private var listaProjetos: List<Projeto>,
    private val projetoViewModel: ProjetoViewModel,
    private val projetoApiService: ProjetoApiService
): RecyclerView.Adapter<ProjetoAdapter.ProjetoViewHolder>() {

    private val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

    class ProjetoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nomeProjeto: TextView = itemView.findViewById(R.id.item_nome_projeto)
        val dataInicial: TextView = itemView.findViewById(R.id.item_data_inicial)
        val dataFinal: TextView = itemView.findViewById(R.id.item_data_final)
        val statusText: TextView = itemView.findViewById(R.id.item_status)
        val adicionar_projeto: Button = itemView.findViewById(R.id.input_adicionar_projeto)
        val editar_projeto: ImageButton = itemView.findViewById(R.id.edit_projeto)
        val excluir_projeto: ImageButton = itemView.findViewById(R.id.excluir_projeto)

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjetoViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.item_projeto, parent, false)
        return ProjetoViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return if (listaProjetos.isEmpty()) 1 else listaProjetos.size
    }

    fun excluirProjeto(position: Int) {
        if (listaProjetos.isNotEmpty()) {
            val projetoExcluido = listaProjetos[position]
            listaProjetos = listaProjetos.filterIndexed { index, _ -> index != position }
            notifyDataSetChanged()
            projetoViewModel.excluirProjeto(projetoApiService, projetoExcluido.id)
        }
    }


    fun updateProjetoList(newList: List<Projeto>) {
        listaProjetos = newList
        notifyDataSetChanged()
    }

    private fun parseDate(dateString: String): String {
        return try {
            val parsedDate = OffsetDateTime.parse(dateString, dateFormatter)
            parsedDate.format(dateFormatter)
        } catch (e: Exception) {
            dateString
        }
    }

    override fun onBindViewHolder(holder: ProjetoViewHolder, position: Int) {
        if (listaProjetos.isEmpty()) {
            holder.adicionar_projeto.visibility = View.VISIBLE

            holder.adicionar_projeto.setOnClickListener {
                holder.itemView.findNavController().navigate(R.id.action_nav_projeto_to_projetoFormFragment)
            }

            holder.nomeProjeto.text = ""
            holder.dataInicial.text = ""
            holder.dataFinal.text = ""
            holder.statusText.text = ""
        } else {
            val projeto = listaProjetos[position]
            holder.nomeProjeto.visibility = View.VISIBLE
            holder.dataInicial.visibility = View.VISIBLE
            holder.dataFinal.visibility = View.VISIBLE
            holder.statusText.visibility = View.VISIBLE
            holder.editar_projeto.visibility = View.VISIBLE
            holder.excluir_projeto.visibility = View.VISIBLE
            holder.adicionar_projeto.visibility = View.GONE

            holder.nomeProjeto.text = projeto.nome
            val prazoInicialFormatted = parseDate(projeto.prazoInicial)
            val prazoFinalFormatted = parseDate(projeto.prazoFinal)
            holder.dataInicial.text = prazoInicialFormatted
            holder.dataFinal.text = prazoFinalFormatted
            holder.statusText.text = projeto.status?.nome

            if (position == 0) {
                holder.adicionar_projeto.visibility = View.VISIBLE
                holder.adicionar_projeto.setOnClickListener {
                    holder.itemView.findNavController().navigate(R.id.action_nav_projeto_to_projetoFormFragment)
                }
            } else {
                holder.adicionar_projeto.visibility = View.GONE
            }

            holder.excluir_projeto.setOnClickListener {
                excluirProjeto(position)
            }

            holder.editar_projeto.setOnClickListener {
                val bundle = Bundle().apply {
                    putSerializable("projeto", projeto)
                }
                holder.itemView.findNavController().navigate(R.id.action_nav_projeto_to_projetoFormFragment, bundle)
            }
        }
    }
}