package com.pedroza.infnet.ecojardimproject.ui.projeto

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.pedroza.infnet.ecojardimproject.R

class ProjetoFormFragment : Fragment() {


    companion object {
        fun newInstance() = ProjetoFormFragment()
    }

    private val viewModel: ProjetoFormViewModel by viewModels()

    private lateinit var statusSpinner: Spinner

    private lateinit var clienteSpinner: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_projeto_form, container, false)
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
        }


    }
}