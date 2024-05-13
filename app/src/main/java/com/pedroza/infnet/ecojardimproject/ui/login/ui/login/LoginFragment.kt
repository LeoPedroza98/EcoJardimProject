package com.pedroza.infnet.ecojardimproject.ui.login.ui.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.pedroza.infnet.ecojardimproject.R
import com.pedroza.infnet.ecojardimproject.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val usernameEditText = binding.username
        val passwordEditText = binding.password
        val loginButton = binding.login
        val cadastraButton = binding.cadastra

        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val usernameInput = usernameEditText.text.toString().trim()
                val passwordInput = passwordEditText.text.toString().trim()

                loginButton.isEnabled = usernameInput.isNotEmpty() && passwordInput.isNotEmpty()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        }

        usernameEditText.addTextChangedListener(textWatcher)
        passwordEditText.addTextChangedListener(textWatcher)



        loginButton.setOnClickListener {
           findNavController().navigate(R.id.action_loginFragment_to_nav_home)
        }

        cadastraButton.isEnabled = true

        cadastraButton.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_cadastroFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}