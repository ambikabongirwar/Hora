package com.example.miniproject1.multiUser

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.miniproject1.R
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment(), View.OnClickListener {

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
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        view.findViewById<Button>(R.id.btnLogin).setOnClickListener(this)
        view.findViewById<Button>(R.id.btnRedirectToRegister).setOnClickListener(this)
        etEmail = view.findViewById<EditText>(R.id.editTextLoginEmailAddress)
        etPassword = view.findViewById<EditText>(R.id.editTextLoginPassword)
        error = view.findViewById(R.id.editTextLoginError)
        mAuth = FirebaseAuth.getInstance()
    }

    override fun onClick(v: View?) {
        when(v!!.id) {
            R.id.btnLogin -> login(v)
            R.id.btnRedirectToRegister -> navController?.navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    fun login(view: View) {
        val email = etEmail.text.toString()
        val password = etPassword.text.toString()
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(requireActivity()) { task ->
            if (task.isSuccessful) {
                navController?.navigate(R.id.action_loginFragment_to_multiUserFragment)
            } else {
                error.text = task.exception?.message.toString()
            }
        }
    }
}