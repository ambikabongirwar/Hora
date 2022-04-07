package com.example.miniproject1.multiUser

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.miniproject1.R

class RegisterFragment : Fragment(), View.OnClickListener {

    var navController: NavController? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        view.findViewById<Button>(R.id.btnRegisterToLogin).setOnClickListener(this)
        view.findViewById<Button>(R.id.btnRegister).setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v!!.id) {
            R.id.btnRegister -> navController!!.navigate(R.id.action_registerFragment_to_multiUserFragment)
            R.id.btnRegisterToLogin -> navController!!.navigate(R.id.action_registerFragment_to_loginFragment)
        }
    }

}