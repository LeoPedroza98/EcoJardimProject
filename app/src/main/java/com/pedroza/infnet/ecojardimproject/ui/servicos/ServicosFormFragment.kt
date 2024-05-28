package com.pedroza.infnet.ecojardimproject.ui.servicos

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.pedroza.infnet.ecojardimproject.R
import com.pedroza.infnet.ecojardimproject.models.Orcamento
import com.pedroza.infnet.ecojardimproject.models.Projeto
import com.pedroza.infnet.ecojardimproject.models.Servico
import com.pedroza.infnet.ecojardimproject.service.JsonPatchOperation
import com.pedroza.infnet.ecojardimproject.service.ProjetoApiService
import com.pedroza.infnet.ecojardimproject.service.RetrofitService
import com.pedroza.infnet.ecojardimproject.service.ServicoApiService
import com.pedroza.infnet.ecojardimproject.ui.projeto.ProjetoFormViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ServicosFormFragment : Fragment() {
    companion object {
        fun newInstance() = ServicosFormFragment()
    }
    private var servicos: Servico? = null
    private var servicoId: Long? = null
    private lateinit var status_Spinner: Spinner
    private lateinit var orcamento_Spinner: Spinner
    private val viewModel: ServicosFormViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_servicos_form, container, false)
        val inputOrcamento = view.findViewById<Spinner>(R.id.orcamento_spinner)
        val inputNomeServico = view.findViewById<TextInputEditText>(R.id.input_nome_servico)
        val inputDescricao = view.findViewById<TextInputEditText>(R.id.input_descricao_servico)
        val inputDataInicio = view.findViewById<TextInputEditText>(R.id.input_data_inicio)
        val inputDataFinalizacao = view.findViewById<TextInputEditText>(R.id.input_data_finalizacao)
        val inputValor = view.findViewById<TextInputEditText>(R.id.input_valor_servico)
        val inputStatus = view.findViewById<Spinner>(R.id.status_spinner)
        val btn_adiciona_servicos = view.findViewById<Button>(R.id.input_salvar_servicos)


        arguments?.let {
            servicos = it.getSerializable("servico") as? Servico
            servicoId = servicos?.id
            servicos?.let { p ->
                inputNomeServico.setText(p.nome)
                inputDescricao.setText(p.descricao)
                inputDataInicio.setText(p.dataInicio)
                inputDataFinalizacao.setText(p.dataFinalizacao)
                inputValor.setText(p.valor.toString())
            }
        }

        btn_adiciona_servicos.setOnClickListener {
            val nomeProjeto = inputNomeServico.text.toString()
            val descricao = inputDescricao.text.toString()
            val dataInicial = inputDataInicio.text.toString()
            val dataFinal = inputDataFinalizacao.text.toString()
            val valor = inputValor.text.toString().toDoubleOrNull() ?: 0.0
            val statusId = viewModel.statusList.value?.get(status_Spinner.selectedItemPosition)?.id ?: 0L
            val orcamentoId = viewModel.orcamentoList.value?.get(orcamento_Spinner.selectedItemPosition)?.id ?: 0L

            viewModel.OnSalvarButtonServicos(nomeProjeto, descricao, dataInicial, dataFinal, valor, statusId, orcamentoId)
        }

        viewModel.saveButtonClickListener.observe(viewLifecycleOwner) { clicked ->
            if (clicked) {
                if (servicoId == null) {
                    createServicos()
                }
                else {
                    updateServico(servicoId!!)
                }
                viewModel._saveButtonClickListener.value = false
            }
        }

        return view
    }

    fun createPatchOperations(servico: Servico): List<JsonPatchOperation> {
        val operations = mutableListOf<JsonPatchOperation>()

        if (servico.orcamentoId != null) {
            operations.add(JsonPatchOperation("replace", "/orcamentoId", servico.orcamentoId as Any))
        }
        if (!servico.nome.isNullOrBlank()) {
            operations.add(JsonPatchOperation("replace", "/nome", servico.nome))
        }
        if (!servico.descricao.isNullOrBlank()) {
            operations.add(JsonPatchOperation("replace", "/descricao", servico.descricao))
        }
        if (!servico.dataInicio.isNullOrBlank()) {
            operations.add(JsonPatchOperation("replace", "/dataInicio", servico.dataInicio))
        }
        if (!servico.dataFinalizacao.isNullOrBlank()) {
            operations.add(JsonPatchOperation("replace", "/dataFinalizacao", servico.dataFinalizacao))
        }
        if (!servico.valor.isNaN()) {
            operations.add(JsonPatchOperation("replace", "/valor", servico.valor))
        }
        if (servico.statusId != null) {
            operations.add(JsonPatchOperation("replace", "/statusId", servico.statusId as Any))
        }
        return operations
    }

    private fun updateServico(servicoId: Long) {
        val service = RetrofitService.apiEcoJardimProject.create(ServicoApiService::class.java)
        val statusSelecionado = viewModel.statusList.value?.firstOrNull { it.nome == status_Spinner.selectedItem.toString() }
        val orcamentoSelecionado = viewModel.orcamentoList.value?.firstOrNull { it.nome == orcamento_Spinner.selectedItem.toString() }

        val servicoAtualizado = Servico(
            id = 0L,
            nome = viewModel.nomeServico.value ?: "",
            descricao = viewModel.descricaoServico.value ?: "",
            valor = viewModel.valorServico.value ?: 0.0,
            statusId = statusSelecionado?.id,
            status = null,
            orcamentoId = orcamentoSelecionado?.id,
            orcamento = null,
            dataInicio = viewModel.dataInicio.value ?: "",
            dataFinalizacao = viewModel.dataFinalizacao.value ?: "",
        )

        val patchOperations = createPatchOperations(servicoAtualizado)
        service.updateServico(servicoId, patchOperations).enqueue(object : Callback<Servico?> {
            override fun onResponse(call: Call<Servico?>, response: Response<Servico?>) {
                if (response.isSuccessful) {
                    findNavController().navigate(R.id.action_servicosFormFragment_to_nav_servicos)
                }
            }
            override fun onFailure(call: Call<Servico?>, t: Throwable) {
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        status_Spinner = view.findViewById(R.id.status_spinner)
        viewModel.getStatusFromApi()

        viewModel.statusList.observe(viewLifecycleOwner) { statusList ->
            val adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                statusList.map { it.nome }
            )
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            status_Spinner.adapter = adapter

            servicos?.status?.let { status ->
                val position = statusList.indexOfFirst { it.id == status.id }
                if (position >= 0) {
                    status_Spinner.setSelection(position)
                }
            }
        }

        orcamento_Spinner = view.findViewById(R.id.orcamento_spinner)

        viewModel.getOrcamentosFromApi()

        viewModel.orcamentoList.observe(viewLifecycleOwner){
                orcamentoList ->
            val  adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                orcamentoList.map { it.nome }
            )
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            orcamento_Spinner.adapter = adapter

            servicos?.orcamento?.let { orcamento ->
                val position = orcamentoList.indexOfFirst { it.id == orcamento.id }
                if (position >= 0) {
                    orcamento_Spinner.setSelection(position)
                }
            }
        }


    }

    private fun createServicos() {
        val service = RetrofitService.apiEcoJardimProject.create(ServicoApiService::class.java)

        val statusSelecionado = viewModel.statusList.value?.firstOrNull { it.nome == status_Spinner.selectedItem.toString() }
        val orcamentoSelecionado = viewModel.orcamentoList.value?.firstOrNull { it.nome == orcamento_Spinner.selectedItem.toString() }

        val servico = Servico(
            id = 0L,
            nome = viewModel.nomeServico.value ?: "",
            descricao = viewModel.descricaoServico.value ?: "",
            valor = viewModel.valorServico.value ?: 0.0,
            statusId = statusSelecionado?.id,
            status = null,
            orcamentoId = orcamentoSelecionado?.id,
            orcamento = null,
            dataInicio = viewModel.dataInicio.value ?: "",
            dataFinalizacao = viewModel.dataFinalizacao.value ?: "",
            )

        service.createServico(servico)?.enqueue(object : Callback<Servico?> {
            override fun onResponse(call: Call<Servico?>, response: Response<Servico?>) {
                if (response.isSuccessful) {
                    Toast.makeText(requireContext(), "Serviço atualizado com sucesso", Toast.LENGTH_SHORT)
                    findNavController().navigate(R.id.action_servicosFormFragment_to_nav_servicos)
                }
            }

            override fun onFailure(call: Call<Servico?>, t: Throwable) {
                Log.e("ServicoFormFragment", "Falha na atualização do cliente: ${t.message}")
            }
        })
        findNavController().navigate(R.id.action_servicosFormFragment_to_nav_servicos)
    }
}