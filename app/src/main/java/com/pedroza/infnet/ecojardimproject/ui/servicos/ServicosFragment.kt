package com.pedroza.infnet.ecojardimproject.ui.servicos

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pedroza.infnet.ecojardimproject.R

class ServicosFragment : Fragment() {

    companion object {
        fun newInstance() = ServicosFragment()
    }

    private val viewModel: ServicosViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_servicos, container, false)
    }
}