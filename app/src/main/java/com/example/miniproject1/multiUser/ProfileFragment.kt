package com.example.miniproject1.multiUser

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.miniproject1.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {

    lateinit var nameTv : TextView
    lateinit var emailTv: TextView
    lateinit var logOutBtn: Button

    lateinit var email: String
    lateinit var db: FirebaseFirestore
    private lateinit var mAuth: FirebaseAuth
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_profile, container, false)
        mAuth = FirebaseAuth.getInstance()
        db = Firebase.firestore
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        val user = mAuth.currentUser
        user.let { email = user?.email.toString() }
        nameTv = view.findViewById(R.id.userNameTv)
        emailTv = view.findViewById(R.id.userEmailTv)
        logOutBtn = view.findViewById(R.id.logoutBtn)
        getData()

        val logOutBtn: Button = view.findViewById(R.id.logoutBtn)
        logOutBtn.setOnClickListener {
            FirebaseAuth.getInstance().signOut();
            navController.navigate(R.id.action_profileFragment_to_registerFragment1)
        }
    }

    fun getData() {
        db.collection("users")
            .whereEqualTo("Email Address", email)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    nameTv.text = document.data["First Name"].toString() + " " + document.data["Last Name"].toString()
                    emailTv.text = "Email: " + email
                }
            }
    }
}