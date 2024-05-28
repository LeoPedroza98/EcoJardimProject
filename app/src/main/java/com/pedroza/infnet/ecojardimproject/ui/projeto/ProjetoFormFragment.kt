package com.pedroza.infnet.ecojardimproject.ui.projeto

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.pedroza.infnet.ecojardimproject.R
import com.pedroza.infnet.ecojardimproject.models.Cliente
import com.pedroza.infnet.ecojardimproject.models.Contato
import com.pedroza.infnet.ecojardimproject.models.Endereco
import com.pedroza.infnet.ecojardimproject.models.Projeto
import com.pedroza.infnet.ecojardimproject.service.ClienteApiService
import com.pedroza.infnet.ecojardimproject.service.JsonPatchOperation
import com.pedroza.infnet.ecojardimproject.service.ProjetoApiService
import com.pedroza.infnet.ecojardimproject.service.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProjetoFormFragment : Fragment() {


    companion object {
        fun newInstance() = ProjetoFormFragment()
    }

    private val viewModel: ProjetoFormViewModel by viewModels()
    private var projeto: Projeto? = null
    private var projetoId: Long? = null

    private lateinit var statusSpinner: Spinner

    private lateinit var clienteSpinner: Spinner


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(R.layout.fragment_projeto_form, container, false)
        val inputCliente = view.findViewById<Spinner>(R.id.clienteSpinner)
        val inputNomeProjeto = view.findViewById<TextInputEditText>(R.id.input_nome_projeto)
        val inputDescricao = view.findViewById<TextInputEditText>(R.id.input_descricao)
        val inputDataInicial = view.findViewById<TextInputEditText>(R.id.input_data_inicial)
        val inputDataFinal = view.findViewById<TextInputEditText>(R.id.input_data_final)
        val inputValor = view.findViewById<TextInputEditText>(R.id.input_valor)
        val inputStatus = view.findViewById<Spinner>(R.id.statusSpinner)
        val btn_adiciona_projeto = view.findViewById<Button>(R.id.btn_salvar_projeto)


        arguments?.let {
            projeto = it.getSerializable("projeto") as? Projeto
            projetoId = projeto?.id
            projeto?.let { p ->
                inputNomeProjeto.setText(p.nome)
                inputDescricao.setText(p.descricao)
                inputDataInicial.setText(p.prazoInicial)
                inputDataFinal.setText(p.prazoFinal)
                inputValor.setText(p.valor.toString())
            }
        }

        btn_adiciona_projeto.setOnClickListener {
            val nomeProjeto = inputNomeProjeto.text.toString()
            val descricao = inputDescricao.text.toString()
            val dataInicial = inputDataInicial.text.toString()
            val dataFinal = inputDataFinal.text.toString()
            val valor = inputValor.text.toString().toDoubleOrNull() ?: 0.0
            val statusId = viewModel.statusList.value?.get(statusSpinner.selectedItemPosition)?.id ?: 0L
            val clienteId = viewModel.clienteList.value?.get(clienteSpinner.selectedItemPosition)?.id ?: 0L

            viewModel.OnSalvarButtonProjetos(nomeProjeto, descricao, dataInicial, dataFinal, valor, statusId, clienteId)
        }

        viewModel.saveButtonClickListener.observe(viewLifecycleOwner) { clicked ->
            if (clicked) {
                if (projetoId == null) {
                    createProjetos()
                }else {
                    updateProjeto(projetoId!!)
                }
                viewModel._saveButtonClickListener.value = false
            }
        }

        return view


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        statusSpinner = view.findViewById(R.id.statusSpinner)
        viewModel.getStatusFromApi()

        viewModel.statusList.observe(viewLifecycleOwner) { statusList ->
            val adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                statusList.map { it.nome }
            )
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            statusSpinner.adapter = adapter

            projeto?.status?.let { status ->
                val position = statusList.indexOfFirst { it.id == status.id }
                if (position >= 0) {
                    statusSpinner.setSelection(position)
                }
            }
        }

        clienteSpinner = view.findViewById(R.id.clienteSpinner)

        viewModel.getClienteFromApi()

        viewModel.clienteList.observe(viewLifecycleOwner){
            clienteList ->
            val  adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                clienteList.map { it.nome }
            )
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            clienteSpinner.adapter = adapter

            projeto?.cliente?.let { cliente ->
                val position = clienteList.indexOfFirst { it.id == cliente.id }
                if (position >= 0) {
                    clienteSpinner.setSelection(position)
                }
            }
        }


    }

    private fun createProjetos(){
        val service = RetrofitService.apiEcoJardimProject.create(ProjetoApiService::class.java)

        val statusSelecionado = viewModel.statusList.value?.firstOrNull { it.nome == statusSpinner.selectedItem.toString() }
        val clienteSelecionado = viewModel.clienteList.value?.firstOrNull { it.nome == clienteSpinner.selectedItem.toString() }

        val projeto = Projeto(
            id = 0L,
            nome = viewModel.nomeProjeto.value ?: "",
            descricao = viewModel.descricao.value ?: "",
            statusId = statusSelecionado?.id,
            status = null,
            prazoInicial = viewModel.dataInicial.value ?: "",
            prazoFinal = viewModel.dataFinal.value ?: "",
            valor = viewModel.valorEstimado.value ?: 0.0,
            clienteId = clienteSelecionado?.id,
            cliente = null,

        )

        service.createProjeto(projeto)?.enqueue(object : Callback<Projeto?> {
            override fun onResponse(call: Call<Projeto?>, response: Response<Projeto?>) {
                if (response.isSuccessful) {
                    findNavController().navigate(R.id.action_projetoFormFragment_to_nav_projeto)
                }
            }

            override fun onFailure(call: Call<Projeto?>, t: Throwable) {
            }
        })
    }

    fun createPatchOperations(projeto: Projeto): List<JsonPatchOperation> {
        val operations = mutableListOf<JsonPatchOperation>()

        if (projeto.clienteId != null) {
            operations.add(JsonPatchOperation("replace", "/clienteId", projeto.clienteId as Any))
        }
        if (!projeto.nome.isNullOrBlank()) {
            operations.add(JsonPatchOperation("replace", "/nome", projeto.nome))
        }
        if (!projeto.descricao.isNullOrBlank()) {
            operations.add(JsonPatchOperation("replace", "/descricao", projeto.descricao))
        }
        if (!projeto.prazoInicial.isNullOrBlank()) {
            operations.add(JsonPatchOperation("replace", "/prazoInicial", projeto.prazoInicial))
        }
        if (!projeto.prazoFinal.isNullOrBlank()) {
            operations.add(JsonPatchOperation("replace", "/prazoFinal", projeto.prazoFinal))
        }
        if (!projeto.valor.isNaN()) {
            operations.add(JsonPatchOperation("replace", "/valor", projeto.valor))
        }
        if (projeto.statusId != null) {
            operations.add(JsonPatchOperation("replace", "/statusId", projeto.statusId as Any))
        }
        return operations
    }

    private fun updateProjeto(projetoId: Long) {
        val service = RetrofitService.apiEcoJardimProject.create(ProjetoApiService::class.java)
        val statusSelecionado = viewModel.statusList.value?.firstOrNull { it.nome == statusSpinner.selectedItem.toString() }
        val clienteSelecionado = viewModel.clienteList.value?.firstOrNull { it.nome == clienteSpinner.selectedItem.toString() }

        val projetoAtualizado = Projeto(
            id = projetoId,
            nome = viewModel.nomeProjeto.value ?: "",
            descricao = viewModel.descricao.value ?: "",
            statusId = statusSelecionado?.id,
            status = null,
            prazoInicial = viewModel.dataInicial.value ?: "",
            prazoFinal = viewModel.dataFinal.value ?: "",
            valor = viewModel.valorEstimado.value ?: 0.0,
            clienteId = clienteSelecionado?.id,
            cliente = null
        )

        val patchOperations = createPatchOperations(projetoAtualizado)
        service.updateProjeto(projetoId, patchOperations).enqueue(object : Callback<Projeto?> {
            override fun onResponse(call: Call<Projeto?>, response: Response<Projeto?>) {
                if (response.isSuccessful) {
                    findNavController().navigate(R.id.action_projetoFormFragment_to_nav_projeto)
                }
            }

            override fun onFailure(call: Call<Projeto?>, t: Throwable) {
            }
        })
    }
}