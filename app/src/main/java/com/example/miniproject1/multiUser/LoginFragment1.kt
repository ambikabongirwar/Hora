package com.example.miniproject1.multiUser

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.miniproject1.R
import com.google.firebase.auth.FirebaseAuth

class LoginFragment1 : Fragment() {

    var navController: NavController? = null
    lateinit var etEmail: EditText
    lateinit var etPassword: EditText
    lateinit var mAuth: FirebaseAuth
    lateinit var error: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_login1, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        etEmail = view.findViewById(R.id.editTextLoginEmailAddress)
        etPassword = view.findViewById(R.id.editTextLoginPassword)
        error = view.findViewById(R.id.editTextLoginError)
        mAuth = FirebaseAuth.getInstance()
        navController = Navigation.findNavController(view)

        val loginBtn: Button = view.findViewById(R.id.btnLogin)
        loginBtn.setOnClickListener{ login() }

        view.findViewById<Button>(R.id.btnRedirectToRegister).setOnClickListener{
            navController!!.navigate(R.id.action_loginFragment1_to_registerFragment1)
        }
    }

    fun login() {
        val email = etEmail.text.toString()
        val password = etPassword.text.toString()
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(requireActivity()) { task ->
            if (task.isSuccessful) {
                navController?.navigate(R.id.action_loginFragment1_to_multiUserFragment1)
            } else {
                error.text = task.exception?.message.toString()
            }
        }
    }
}