package com.pedroza.infnet.ecojardimproject.ui.orcamentos

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsetsAnimation.Callback
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.pedroza.infnet.ecojardimproject.R
import com.pedroza.infnet.ecojardimproject.models.Orcamento
import com.pedroza.infnet.ecojardimproject.models.Projeto
import com.pedroza.infnet.ecojardimproject.service.JsonPatchOperation
import com.pedroza.infnet.ecojardimproject.service.OrcamentoApiService
import com.pedroza.infnet.ecojardimproject.service.ProjetoApiService
import com.pedroza.infnet.ecojardimproject.service.RetrofitService
import com.pedroza.infnet.ecojardimproject.ui.projeto.ProjetoFormViewModel
import org.threeten.bp.LocalDateTime
import retrofit2.Call
import retrofit2.Response

class OrcamentosFormFragment : Fragment() {
    private val viewModel: OrcamentosFormViewModel by viewModels()

    private var orcamento: Orcamento? = null
    private var orcamentoId: Long? = null

    private lateinit var projetoSpinner: Spinner

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_orcamentos_form, container, false)
        val inputProjeto = view.findViewById<Spinner>(R.id.projetoSpinner)
        val inputNomeServico = view.findViewById<TextInputEditText>(R.id.input_nome_orcamento)
        val inputDescricaoServico = view.findViewById<TextInputEditText>(R.id.input_descricao_orcamento)
        val btn_salva = view.findViewById<Button>(R.id.btn_salvar)

        arguments?.let {
            orcamento = it.getSerializable("orcamento") as? Orcamento
            orcamentoId = orcamento?.id
            orcamento?.let { p ->
                inputNomeServico.setText(p.nome)
                inputDescricaoServico.setText(p.descricao)
            }
        }

        btn_salva.setOnClickListener {
            val nomeServico = inputNomeServico.text.toString()
            val descricao = inputDescricaoServico.text.toString()
            val projetoId = viewModel.projetoList.value?.get(projetoSpinner.selectedItemPosition)?.id ?: 0L

            viewModel.OnSalvarButtonOrcamentos(nomeServico, descricao,projetoId)
        }

        viewModel.saveButtonClickListener.observe(viewLifecycleOwner) { clicked ->
            if (clicked) {
                if (orcamentoId == null) {
                    createOrcamentos()
                }else {
                    updateOrcamento(orcamentoId!!)
                }
                viewModel._saveButtonClickListener.value = false
            }
        }



        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        projetoSpinner = view.findViewById(R.id.projetoSpinner)

        viewModel.getProjetoFromApi()

        viewModel.projetoList.observe(viewLifecycleOwner){
                projetoList ->
            val  adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                projetoList.map { it.nome }
            )
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            projetoSpinner.adapter = adapter

            orcamento?.projeto?.let { projeto ->
                val position = projetoList.indexOfFirst { it.id == projeto.id }
                if (position >= 0) {
                    projetoSpinner.setSelection(position)
                }
            }
        }
    }

    private fun createOrcamentos() {
        val service = RetrofitService.apiEcoJardimProject.create(OrcamentoApiService::class.java)

        val projetoSelecionado = viewModel.projetoList.value?.firstOrNull { it.nome == projetoSpinner.selectedItem.toString() }

        val orcamento = Orcamento(
            id = 0L,
            nome = viewModel.nomeServico.value ?: "",
            dataCriacao = LocalDateTime.now().toString(),
            descricao = viewModel.descricaoServico.value?: "",
            projetoId = projetoSelecionado?.id,
            projeto = null,
            servicos = emptyList()
        )

        service.createOrcamento(orcamento)?.enqueue(object : retrofit2.Callback<Orcamento?> {
            override fun onResponse(call: Call<Orcamento?>, response: Response<Orcamento?>) {
                if (response.isSuccessful) {
                    findNavController().navigate(R.id.action_orcamentosFormFragment_to_nav_orcamentos)
                }
            }

            override fun onFailure(call: Call<Orcamento?>, t: Throwable) {
            }
        })
    }

    fun createPatchOperations(orcamento: Orcamento): List<JsonPatchOperation> {
        val operations = mutableListOf<JsonPatchOperation>()

        if (orcamento.projetoId != null) {
            operations.add(JsonPatchOperation("replace", "/projetoId", orcamento.projetoId as Any))
        }
        if (!orcamento.nome.isNullOrBlank()) {
            operations.add(JsonPatchOperation("replace", "/nome", orcamento.nome))
        }
        if (!orcamento.descricao.isNullOrBlank()) {
            operations.add(JsonPatchOperation("replace", "/descricao", orcamento.descricao))
        }
        return operations
    }

    private fun updateOrcamento(orcamentoId: Long) {
        val service = RetrofitService.apiEcoJardimProject.create(OrcamentoApiService::class.java)
        val projetoSelecionado = viewModel.projetoList.value?.firstOrNull { it.nome == projetoSpinner.selectedItem.toString() }

        val orcamentoAtualizado = Orcamento(
            id = 0L,
            nome = viewModel.nomeServico.value ?: "",
            dataCriacao = LocalDateTime.now().toString(),
            descricao = viewModel.descricaoServico.value?: "",
            projetoId = projetoSelecionado?.id,
            projeto = null,
            servicos = emptyList()
        )

        val patchOperations = createPatchOperations(orcamentoAtualizado)
        service.updateOrcamento(orcamentoId, patchOperations).enqueue(object :
            retrofit2.Callback<Orcamento?> {
            override fun onResponse(call: Call<Orcamento?>, response: Response<Orcamento?>) {
                if (response.isSuccessful) {
                    findNavController().navigate(R.id.action_projetoFormFragment_to_nav_projeto)
                }
            }

            override fun onFailure(call: Call<Orcamento?>, t: Throwable) {
            }
        })
    }
}


