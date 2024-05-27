package com.pedroza.infnet.ecojardimproject.ui.projeto

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.pedroza.infnet.ecojardimproject.R
import com.pedroza.infnet.ecojardimproject.adapter.ProjetoAdapter
import com.pedroza.infnet.ecojardimproject.databinding.FragmentClienteBinding
import com.pedroza.infnet.ecojardimproject.databinding.FragmentProjetoBinding
import com.pedroza.infnet.ecojardimproject.service.ClienteApiService
import com.pedroza.infnet.ecojardimproject.service.ProjetoApiService
import com.pedroza.infnet.ecojardimproject.service.RetrofitService
import com.pedroza.infnet.ecojardimproject.ui.cliente.ClienteViewModel

class ProjetoFragment : Fragment() {

    private var _binding: FragmentProjetoBinding? = null
    private val binding get() = _binding!!
    private lateinit var projetoViewModel: ProjetoViewModel
    private lateinit var projetoAdapter: ProjetoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProjetoBinding.inflate(inflater, container, false)
        projetoViewModel = ViewModelProvider(requireActivity()).get(ProjetoViewModel::class.java)

        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val swipeRefreshLayout = view.findViewById<SwipeRefreshLayout>(R.id.swipe_list_projeto)
        val recyclerView = binding.projetoRecycler
        val navController = findNavController()

        swipeRefreshLayout.setOnRefreshListener {
            projetoViewModel.carregarProjetos(RetrofitService.apiEcoJardimProject.create(
                ProjetoApiService::class.java))
            swipeRefreshLayout.isRefreshing = false
        }

        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        projetoAdapter = ProjetoAdapter(emptyList(), projetoViewModel, RetrofitService.apiEcoJardimProject.create(ProjetoApiService::class.java))

        recyclerView.adapter = projetoAdapter

        projetoViewModel.projeto.observe(viewLifecycleOwner, { projetos ->
            projetoAdapter.updateProjetoList(projetos)
        })

        projetoViewModel.erro.observe(viewLifecycleOwner, { mensagemErro ->
            Toast.makeText(requireContext(), mensagemErro, Toast.LENGTH_LONG).show()
            println(mensagemErro)
        })

        projetoViewModel.carregarProjetos(RetrofitService.apiEcoJardimProject.create(
            ProjetoApiService::class.java))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
