package com.pedroza.infnet.ecojardimproject.ui.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.pedroza.infnet.ecojardimproject.R
import com.pedroza.infnet.ecojardimproject.databinding.FragmentCadastroBinding


class CadastroFragment : Fragment() {
    private var _binding: FragmentCadastroBinding? = null
    private lateinit var auth: FirebaseAuth

    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCadastroBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val email = binding.emailCadastro
        val senha = binding.senhaCadastro
        val cadastroButton = binding.buttonCadastro

        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val usernameInput = email.text.toString().trim()
                val passwordInput = senha.text.toString().trim()

                cadastroButton.isEnabled = usernameInput.isNotEmpty() && passwordInput.isNotEmpty()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        }

        email.addTextChangedListener(textWatcher)
        senha.addTextChangedListener(textWatcher)

        cadastroButton.setOnClickListener {
            val email = email.text.toString().trim()
            val password = senha.text.toString().trim()

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val message = "Cadastro realizado com sucesso!"
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_cadastroFragment_to_loginFragment)
                    } else {
                        val errorMessage = task.exception?.message.toString()
                        Toast.makeText(context, "Erro ao cadastrar: $errorMessage", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
