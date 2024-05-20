package com.pedroza.infnet.ecojardimproject.ui.cliente

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.pedroza.infnet.ecojardimproject.R

class ClienteFormFragment : Fragment() {

    private lateinit var viewPager: ViewPager2
    private lateinit var btnProximoEndereco: Button

    companion object {
        fun newInstance() = ClienteFormFragment()
    }

    private val viewModel: ClienteFormViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_cliente_form, container, false)
        viewPager = view.findViewById(R.id.viewPage)
        viewPager.adapter = ClienteFormPagerAdapter(activity as FragmentActivity)
        return view
    }

    private inner class ClienteFormPagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = 3

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> ClienteDadosFragment()
                1 -> ClienteEnderecoFragment()
                2 -> ClienteContatoFragment()
                else -> throw IllegalStateException("Unexpected position $position")
            }
        }
    }
}