package com.example.miniproject1.multiUser

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.miniproject1.R
import com.google.firebase.auth.FirebaseAuth

class RegisterFragment : Fragment(), View.OnClickListener {

    lateinit var navController: NavController
    private lateinit var mAuth: FirebaseAuth
    lateinit var email_id_edt: EditText
    lateinit var password_edt: EditText
    lateinit var confirm_password_edt: EditText
    val TAG = "RegisterFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_register, container, false)
        mAuth = FirebaseAuth.getInstance()
        Log.d(TAG, "onCreateView")
        return view
    }

    override fun onStart() {
        Log.d(TAG, "OnStarted")
        super.onStart()
        val currentUser = mAuth.currentUser
        if (currentUser != null) {
            navController.navigate(R.id.action_registerFragment_to_multiUserFragment)
        }
        Log.d(TAG, "OnStartEnded")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "On View Created")
        email_id_edt = view.findViewById(R.id.editTextRegisterEmailAddress)
        password_edt = view.findViewById(R.id.editTextRegisterPassword)
        confirm_password_edt = view.findViewById(R.id.editTextRegisterConfirmPassword)
        navController = Navigation.findNavController(view)
        view.findViewById<Button>(R.id.btnRegisterToLogin).setOnClickListener(this)
        Log.d(TAG, "After register to login button in on view created")
        view.findViewById<Button>(R.id.btnRegister).setOnClickListener(this)
        Log.d(TAG, "After register in on view created")
    }

    override fun onClick(v: View?) {
        Log.d(TAG, "Inside On click")
        when(v!!.id) {
            R.id.btnRegister -> register(v)
            R.id.btnRegisterToLogin -> navController.navigate(R.id.action_registerFragment_to_loginFragment)
        }
    }

    fun register(v: View){
        Log.d(TAG, "inside register function")
        val email = email_id_edt.text.toString()
        Log.d(TAG, "got email")
        val password = password_edt.text.toString()
        Log.d(TAG, "got password")
        val confirmPassword = confirm_password_edt.text.toString()
        Log.d(TAG, "input email and pasword")
        if (password == confirmPassword) {
            mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity()) {task ->
                    if(task.isSuccessful) {
                        navController.navigate(R.id.action_registerFragment_to_multiUserFragment)
                    }
                    else {
                        Toast.makeText(context, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()
                    }
                }
        }
        else {
            Toast.makeText(context, "Password and Confirm Password do not match",
                Toast.LENGTH_SHORT).show()
        }
    }
}