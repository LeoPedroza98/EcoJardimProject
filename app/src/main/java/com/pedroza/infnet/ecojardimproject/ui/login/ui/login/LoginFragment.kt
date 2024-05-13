package com.pedroza.infnet.ecojardimproject.ui.login.ui.login

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
import com.pedroza.infnet.ecojardimproject.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
    }

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
            val usernameInput = usernameEditText.text.toString().trim()
            val passwordInput = passwordEditText.text.toString().trim()

            val task = auth.signInWithEmailAndPassword(usernameInput, passwordInput)
            task.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    findNavController().navigate(R.id.action_loginFragment_to_nav_home)
                } else {
                    val exception = task.exception
                    Toast.makeText(requireContext(), exception?.message ?: "Authentication failed", Toast.LENGTH_SHORT).show()
                }
            }
        }

        cadastraButton.isEnabled = true

        cadastraButton.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_cadastroFragment)
        }
    }

    private fun isUserValid(username: String, password: String): Boolean {
        val task = auth.signInWithEmailAndPassword(username, password)
        task.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                return@addOnCompleteListener  // Return true if user is valid
            } else {
                // Handle error (already implemented in your code)
                return@addOnCompleteListener  // Return false if user is not valid
            }
        }
        // This line is not reached as the method returns within onComplete
        return false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}