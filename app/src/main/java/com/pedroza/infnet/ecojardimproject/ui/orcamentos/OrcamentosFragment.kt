package com.pedroza.infnet.ecojardimproject.ui.orcamentos

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
import com.pedroza.infnet.ecojardimproject.adapter.OrcamentoAdapter
import com.pedroza.infnet.ecojardimproject.adapter.ProjetoAdapter
import com.pedroza.infnet.ecojardimproject.databinding.FragmentOrcamentosBinding
import com.pedroza.infnet.ecojardimproject.databinding.FragmentProjetoBinding
import com.pedroza.infnet.ecojardimproject.service.OrcamentoApiService
import com.pedroza.infnet.ecojardimproject.service.ProjetoApiService
import com.pedroza.infnet.ecojardimproject.service.RetrofitService
import com.pedroza.infnet.ecojardimproject.ui.projeto.ProjetoViewModel

class OrcamentosFragment : Fragment() {
    private var _binding: FragmentOrcamentosBinding? = null
    private val binding get() = _binding!!
    private lateinit var orcamentoViewModel: OrcamentosViewModel
    private lateinit var orcamentoAdapter: OrcamentoAdapter

    private val viewModel: OrcamentosViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrcamentosBinding.inflate(inflater, container, false)
        orcamentoViewModel = ViewModelProvider(requireActivity()).get(OrcamentosViewModel::class.java)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val swipeRefreshLayout = view.findViewById<SwipeRefreshLayout>(R.id.swipe_list_orcamentos)
        val recyclerView = binding.orcamentosRecycler
        val navController = findNavController()

        swipeRefreshLayout.setOnRefreshListener {
            orcamentoViewModel.carregarOrcamentos(
                RetrofitService.apiEcoJardimProject.create(
                OrcamentoApiService::class.java))
            swipeRefreshLayout.isRefreshing = false
        }

        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        orcamentoAdapter = OrcamentoAdapter(emptyList(), orcamentoViewModel, RetrofitService.apiEcoJardimProject.create(OrcamentoApiService::class.java))

        recyclerView.adapter = orcamentoAdapter

        orcamentoViewModel.orcamento.observe(viewLifecycleOwner, { orcmamentos ->
            orcamentoAdapter.updateOrcamentoList(orcmamentos)
        })

        orcamentoViewModel.erro.observe(viewLifecycleOwner, { mensagemErro ->
            Toast.makeText(requireContext(), mensagemErro, Toast.LENGTH_LONG).show()
            println(mensagemErro)
        })

        orcamentoViewModel.carregarOrcamentos(RetrofitService.apiEcoJardimProject.create(
            OrcamentoApiService::class.java))

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}