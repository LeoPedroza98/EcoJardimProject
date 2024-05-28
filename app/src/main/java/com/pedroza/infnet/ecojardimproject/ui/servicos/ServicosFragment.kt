package com.pedroza.infnet.ecojardimproject.ui.servicos

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.pedroza.infnet.ecojardimproject.R
import com.pedroza.infnet.ecojardimproject.adapter.ProjetoAdapter
import com.pedroza.infnet.ecojardimproject.adapter.ServicosAdapter
import com.pedroza.infnet.ecojardimproject.databinding.FragmentProjetoBinding
import com.pedroza.infnet.ecojardimproject.databinding.FragmentServicosBinding
import com.pedroza.infnet.ecojardimproject.service.ProjetoApiService
import com.pedroza.infnet.ecojardimproject.service.RetrofitService
import com.pedroza.infnet.ecojardimproject.service.ServicoApiService
import com.pedroza.infnet.ecojardimproject.ui.projeto.ProjetoViewModel

class ServicosFragment : Fragment() {

    private var _binding: FragmentServicosBinding? = null
    private val binding get() = _binding!!
    private lateinit var servicoViewModel: ServicosViewModel
    private lateinit var servicosAdapter: ServicosAdapter

    private val viewModel: ServicosViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val swipeRefreshLayout = view.findViewById<SwipeRefreshLayout>(R.id.swipe_list_servicos)
        val recyclerView = binding.servicosRecycler
        val navController = findNavController()

        swipeRefreshLayout.setOnRefreshListener {
            servicoViewModel.carregarServicos(
                RetrofitService.apiEcoJardimProject.create(
                ServicoApiService::class.java))
            swipeRefreshLayout.isRefreshing = false
        }

        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        servicosAdapter = ServicosAdapter(emptyList(), servicoViewModel, RetrofitService.apiEcoJardimProject.create(ServicoApiService::class.java))

        recyclerView.adapter = servicosAdapter

        servicoViewModel.servico.observe(viewLifecycleOwner, { servicos ->
            servicosAdapter.updateServicoList(servicos)
        })

        servicoViewModel.erro.observe(viewLifecycleOwner, { mensagemErro ->
            Toast.makeText(requireContext(), mensagemErro, Toast.LENGTH_LONG).show()
            println(mensagemErro)
        })

        servicoViewModel.carregarServicos(RetrofitService.apiEcoJardimProject.create(
            ServicoApiService::class.java))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentServicosBinding.inflate(inflater, container, false)
        servicoViewModel = ViewModelProvider(requireActivity()).get(ServicosViewModel::class.java)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}