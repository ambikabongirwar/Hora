package com.example.miniproject1.multiUser

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.miniproject1.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.ktx.Firebase


class MultiUserFragment : Fragment() {

    lateinit var mAuth: FirebaseAuth
    lateinit var logOut: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_multi_user, container, false)
        mAuth = FirebaseAuth.getInstance();
        val currentUser = mAuth.currentUser
        val textView: TextView = view.findViewById(R.id.email_address)
        logOut = view.findViewById(R.id.btnLogOut)
        if (currentUser == null) {
            textView.text = "MultiUser Fragment no user logged in"
        }
        else {
            updateUI(view, currentUser, textView)

        }
        return view
    }


    fun updateUI(view: View, currentUser: FirebaseUser?, textView: TextView) {
        textView.text = "Hello" + currentUser!!.email
        logOut.setOnClickListener{
            FirebaseAuth.getInstance().signOut();

        }
    }
}