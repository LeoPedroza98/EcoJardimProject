package com.pedroza.infnet.ecojardimproject.ui.cliente

import ClienteAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pedroza.infnet.ecojardimproject.R
import com.pedroza.infnet.ecojardimproject.databinding.FragmentClienteBinding
import com.pedroza.infnet.ecojardimproject.models.Cliente
import com.pedroza.infnet.ecojardimproject.service.ClienteApiService
import com.pedroza.infnet.ecojardimproject.service.RetrofitService

class ClienteFragment : Fragment() {

    private var _binding: FragmentClienteBinding? = null
    private val binding get() = _binding!!
    private lateinit var clienteViewModel: ClienteViewModel
    private lateinit var clienteAdapter: ClienteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentClienteBinding.inflate(inflater, container, false)
        clienteViewModel = ViewModelProvider(requireActivity()).get(ClienteViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Obtem a referencia para o RecyclerView
        val recyclerView = binding.clientRecycler

        // Configura o RecyclerView com um layout manager linear
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Inicializa o adapter com uma lista vazia
        clienteAdapter = ClienteAdapter(emptyList())

        // Define o adapter para o RecyclerView
        recyclerView.adapter = clienteAdapter

        // Observa as alterações na lista de clientes
        clienteViewModel.clientes.observe(viewLifecycleOwner, { clientes ->
            // Atualiza o adapter com a nova lista de clientes
            clienteAdapter.updateClientesList(clientes)
        })

        // Observa os erros durante a requisição de dados
        clienteViewModel.erro.observe(viewLifecycleOwner, { mensagemErro ->
            Toast.makeText(requireContext(), mensagemErro, Toast.LENGTH_LONG).show()
            println(mensagemErro)
        })

        // Carrega a lista de clientes ao iniciar a tela
        clienteViewModel.carregarClientes(RetrofitService.apiEcoJardimProject.create(
            ClienteApiService::class.java))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
