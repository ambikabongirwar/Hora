package com.example.miniproject1.multiUser

import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.miniproject1.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MultiUserFragment1 : Fragment() {

    private lateinit var navController: NavController
    private lateinit var mAuth: FirebaseAuth
    lateinit var db: FirebaseFirestore
    var TAG = "MultiUserFragment1"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_multi_user1, container, false)
        mAuth = FirebaseAuth.getInstance()
        db = Firebase.firestore
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        val logOutBtn: ImageButton = view.findViewById(R.id.ibLogOut)
        logOutBtn.setOnClickListener {
            FirebaseAuth.getInstance().signOut();
            navController.navigate(R.id.action_multiUserFragment1_to_registerFragment1)
        }
    }
}