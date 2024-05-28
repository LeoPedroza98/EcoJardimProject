package com.pedroza.infnet.ecojardimproject.ui.cliente

import com.pedroza.infnet.ecojardimproject.R
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.pedroza.infnet.ecojardimproject.models.Cliente
import com.pedroza.infnet.ecojardimproject.models.Contato
import com.pedroza.infnet.ecojardimproject.models.Endereco
import com.pedroza.infnet.ecojardimproject.service.ClienteApiService
import com.pedroza.infnet.ecojardimproject.service.JsonPatchOperation
import com.pedroza.infnet.ecojardimproject.service.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ClienteFormFragment : Fragment() {

    companion object {
        fun newInstance() = ClienteFormFragment()
    }

    private val viewModel: ClienteFormViewModel by viewModels()
    private var cliente: Cliente? = null
    private var clienteId: Long? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_cliente_form, container, false)
        val inputNome = view.findViewById<TextInputEditText>(R.id.input_nome)
        val inputSobrenome = view.findViewById<TextInputEditText>(R.id.input_sobrenome)
        val inputDocumento = view.findViewById<TextInputEditText>(R.id.input_documento)
        val buttonSalvar = view.findViewById<Button>(R.id.button_salvar_cliente)

        arguments?.let {
            cliente = it.getSerializable("cliente") as? Cliente
            clienteId = cliente?.id
            cliente?.let { c ->
                inputNome.setText(c.nome)
                inputSobrenome.setText(c.sobrenome)
                inputDocumento.setText(c.documento)
            }
        }

        buttonSalvar.setOnClickListener {
            val nome = inputNome.text.toString()
            val sobrenome = inputSobrenome.text.toString()
            val documento = inputDocumento.text.toString()
            viewModel.onSalvarButtonClicked(nome, sobrenome, documento)
        }

        viewModel.saveButtonClickListener.observe(viewLifecycleOwner) { clicked ->
            if (clicked) {
                if (clienteId == null) {
                    createCliente()
                } else {
                    updateCliente(clienteId!!)
                }
                viewModel.resetSaveButtonClickListener()
            }
        }
        return view
    }

    private fun createCliente() {
        val service = RetrofitService.apiEcoJardimProject.create(ClienteApiService::class.java)

        val cliente = Cliente(
            id = 0L,
            nome = viewModel.nome.value ?: "",
            sobrenome = viewModel.sobrenome.value ?: "",
            documento = viewModel.documento.value ?: "",
            endereco = Endereco(
                logradouro = "",
                numero = "",
                complemento = "",
                cep = "",
                bairro = "",
                municipio = ""
            ),
            contato = Contato(
                nome = "",
                telefone = "",
                celular = "",
                email = ""
            ),
            projetos = emptyList()
        )

        service.createCliente(cliente)?.enqueue(object : Callback<Cliente?> {
            override fun onResponse(call: Call<Cliente?>, response: Response<Cliente?>) {
                if (response.isSuccessful) {
                    findNavController().navigate(R.id.action_clienteFormFragment_to_nav_cliente)
                }
            }

            override fun onFailure(call: Call<Cliente?>, t: Throwable) {
            }
        })
    }

    fun createPatchOperations(cliente: Cliente): List<JsonPatchOperation> {
        val operations = mutableListOf<JsonPatchOperation>()

        if (!cliente.nome.isNullOrBlank()) {
            operations.add(JsonPatchOperation("replace", "/nome", cliente.nome))
        }
        if (!cliente.sobrenome.isNullOrBlank()) {
            operations.add(JsonPatchOperation("replace", "/sobrenome", cliente.sobrenome))
        }
        if (!cliente.documento.isNullOrBlank()) {
            operations.add(JsonPatchOperation("replace", "/documento", cliente.documento))
        }

        return operations
    }
    private fun updateCliente(clienteId: Long) {
        val service = RetrofitService.apiEcoJardimProject.create(ClienteApiService::class.java)
        val nome = view?.findViewById<TextInputEditText>(R.id.input_nome)?.text.toString()
        val sobrenome = view?.findViewById<TextInputEditText>(R.id.input_sobrenome)?.text.toString()
        val documento = view?.findViewById<TextInputEditText>(R.id.input_documento)?.text.toString()

        val clienteAtualizado = Cliente(
            id = clienteId,
            nome = nome,
            sobrenome = sobrenome,
            documento = documento,
            endereco = Endereco(
                logradouro = "",
                numero = "",
                complemento = "",
                cep = "",
                bairro = "",
                municipio = ""
            ),
            contato = Contato(
                nome = "",
                telefone = "",
                celular = "",
                email = ""
            ),
            projetos = emptyList()
        )

        val patchOperations = createPatchOperations(clienteAtualizado)
        service.updateCliente(clienteId, patchOperations).enqueue(object : Callback<Cliente?> {
            override fun onResponse(call: Call<Cliente?>, response: Response<Cliente?>) {
                if (response.isSuccessful) {
                    findNavController().navigate(R.id.action_clienteFormFragment_to_nav_cliente)
                }else{
                    Log.e("ClienteFormFragment", "Erro na atualização do cliente: ${response.errorBody()?.string()}")
                }
            }
            override fun onFailure(call: Call<Cliente?>, t: Throwable) {
            }
        })
    }
}
