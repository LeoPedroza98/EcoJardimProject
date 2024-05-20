package com.pedroza.infnet.ecojardimproject.ui.cliente

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.pedroza.infnet.ecojardimproject.R
import com.pedroza.infnet.ecojardimproject.models.Cliente
import com.pedroza.infnet.ecojardimproject.models.Contato
import com.pedroza.infnet.ecojardimproject.models.Endereco
import com.pedroza.infnet.ecojardimproject.service.ClienteApiService
import com.pedroza.infnet.ecojardimproject.service.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ClienteFormFragment : Fragment() {

    companion object {
        fun newInstance() = ClienteFormFragment()
    }

    private val viewModel: ClienteFormViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_cliente_form, container, false)
        val inputNome = view.findViewById<TextInputEditText>(R.id.input_nome)
        val inputSobrenome = view.findViewById<TextInputEditText>(R.id.input_sobrenome)
        val inputDocumento = view.findViewById<TextInputEditText>(R.id.input_documento)
        val buttonSalvar = view.findViewById<Button>(R.id.button_salvar_cliente)

        buttonSalvar.setOnClickListener {
            val nome = inputNome.text.toString()
            val sobrenome = inputSobrenome.text.toString()
            val documento = inputDocumento.text.toString()
            viewModel.onSalvarButtonClicked(nome, sobrenome, documento)
        }

        viewModel.saveButtonClickListener.observe(viewLifecycleOwner) { clicked ->
            if (clicked) {
                createCliente()
                viewModel._saveButtonClickListener.value = false // Reset click state
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
                // Tratamento de falha na comunicação com a API
            }
        })
    }
}
