package com.example.miniproject1.multiUser

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.miniproject1.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.ktx.Firebase


class MultiUserFragment : Fragment(), View.OnClickListener {

    lateinit var navController: NavController
    lateinit var mAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_multi_user, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        mAuth = FirebaseAuth.getInstance();
        val currentUser = mAuth.currentUser
        val textView: TextView = view.findViewById(R.id.email_address)
        view.findViewById<ImageButton>(R.id.ibLogOut).setOnClickListener(this)
        if (currentUser == null) {
            textView.text = "MultiUser Fragment no user logged in"
        }
        else {
            updateUI(view, currentUser, textView)
        }
    }

    fun updateUI(view: View, currentUser: FirebaseUser?, textView: TextView) {
        textView.text = "Hello" + currentUser!!.email
    }

    override fun onClick(view: View?) {
        when(view?.id) {
            R.id.ibLogOut -> logoutAndRedirect()
        }
    }

    fun logoutAndRedirect() {
        Log.d("MultiUserFragment", "inside logout and redirect")
        FirebaseAuth.getInstance().signOut();
        navController.navigate(R.id.action_multiUserFragment_to_registerFragment)
    }
}