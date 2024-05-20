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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.pedroza.infnet.ecojardimproject.R
import com.pedroza.infnet.ecojardimproject.databinding.FragmentClienteBinding
import com.pedroza.infnet.ecojardimproject.models.Cliente
import com.pedroza.infnet.ecojardimproject.service.ClienteApiService
import com.pedroza.infnet.ecojardimproject.service.RetrofitService
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController

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
        val swipeRefreshLayout = view.findViewById<SwipeRefreshLayout>(R.id.swipe_list_client)
        val recyclerView = binding.clientRecycler
        val navController = findNavController()

        swipeRefreshLayout.setOnRefreshListener {
            clienteViewModel.carregarClientes(RetrofitService.apiEcoJardimProject.create(
                ClienteApiService::class.java))
            swipeRefreshLayout.isRefreshing = false
        }

        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        clienteAdapter = ClienteAdapter(emptyList())

        recyclerView.adapter = clienteAdapter

        clienteViewModel.clientes.observe(viewLifecycleOwner, { clientes ->
            clienteAdapter.updateClientesList(clientes)
        })

        clienteViewModel.erro.observe(viewLifecycleOwner, { mensagemErro ->
            Toast.makeText(requireContext(), mensagemErro, Toast.LENGTH_LONG).show()
            println(mensagemErro)
        })

        clienteViewModel.carregarClientes(RetrofitService.apiEcoJardimProject.create(
            ClienteApiService::class.java))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
