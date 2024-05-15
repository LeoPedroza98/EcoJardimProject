package com.pedroza.infnet.ecojardimproject.ui.orcamentos

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pedroza.infnet.ecojardimproject.R

class OrcamentosFragment : Fragment() {

    companion object {
        fun newInstance() = OrcamentosFragment()
    }

    private val viewModel: OrcamentosViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_orcamentos, container, false)
    }
}