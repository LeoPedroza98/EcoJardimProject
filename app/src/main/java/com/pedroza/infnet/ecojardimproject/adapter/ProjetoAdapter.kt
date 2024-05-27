package com.pedroza.infnet.ecojardimproject.adapter

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

    class ProjetoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nomeProjeto: TextView = itemView.findViewById(R.id.item_nome_projeto)
        val dataInicial: TextView = itemView.findViewById(R.id.item_data_inicial)
        val dataFinal: TextView = itemView.findViewById(R.id.item_data_final)
        val statusText: TextView = itemView.findViewById(R.id.item_status)
        val adicionar_projeto: Button = itemView.findViewById(R.id.input_adicionar_projeto)

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjetoViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.item_projeto, parent, false)
        return ProjetoViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return listaProjetos.size
    }

    fun updateProjetoList(newList: List<Projeto>) {
        listaProjetos = newList
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ProjetoViewHolder, position: Int) {
        val projeto = listaProjetos[position]
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val prazoInicial = OffsetDateTime.parse(projeto.prazoInicial)
        val prazoFinal = OffsetDateTime.parse(projeto.prazoFinal)

        holder.nomeProjeto.text = projeto.nome
        holder.dataInicial.text = prazoInicial.format(formatter)
        holder.dataFinal.text = prazoFinal.format(formatter)
        holder.statusText.text = projeto.status?.nome

        holder.adicionar_projeto.setOnClickListener {
            holder.itemView.findNavController().navigate(R.id.action_nav_projeto_to_projetoFormFragment)
        }

        if (position == 0) {
            holder.adicionar_projeto.visibility = View.VISIBLE
        } else {
            holder.adicionar_projeto.visibility = View.GONE
        }

    }
}