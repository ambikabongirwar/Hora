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
import androidx.navigation.fragment.findNavController
import com.example.miniproject1.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RegisterFragment1 : Fragment() {

    private lateinit var navController: NavController
    private lateinit var mAuth: FirebaseAuth
    lateinit var db: FirebaseFirestore
    lateinit var email_id_edt: EditText
    lateinit var password_edt: EditText
    lateinit var confirm_password_edt: EditText
    lateinit var firstName_edt: EditText
    lateinit var lastName_edt: EditText
    val TAG = "RegisterFragment1"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_register1, container, false)
        mAuth = FirebaseAuth.getInstance()
        db = Firebase.firestore
        /*val currentUser = mAuth.currentUser
        if (currentUser != null) {
            navController.navigate(R.id.action_registerFragment1_to_multiUserFragment1)
        }*/
        //else {
        //}
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        val currentUser = mAuth.currentUser
        if (currentUser != null) {
            //If user is already logged in
            navController.navigate(R.id.action_registerFragment1_to_multiUserFragment1)
        }
        else {
            firstName_edt = view.findViewById(R.id.editTextRegisterFirstName)
            lastName_edt = view.findViewById(R.id.editTextRegisterLastName)
            email_id_edt = view.findViewById(R.id.editTextRegisterEmailAddress)
            password_edt = view.findViewById(R.id.editTextRegisterPassword)
            confirm_password_edt = view.findViewById(R.id.editTextRegisterConfirmPassword)

            val register: Button = view.findViewById(R.id.btnRegister)
            register.setOnClickListener { register() }

            val login: Button = view.findViewById(R.id.btnRegisterToLogin)
            Log.d(TAG, "login button found")
            login.setOnClickListener {
                navController.navigate(R.id.action_registerFragment1_to_loginFragment1)
            }
        }
    }

    fun register(){
        val firstName = firstName_edt.text.toString()
        val lastName = lastName_edt.text.toString()
        val email = email_id_edt.text.toString()
        val password = password_edt.text.toString()
        val confirmPassword = confirm_password_edt.text.toString()
        if (password == confirmPassword) {
            mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity()) {task ->
                    if(task.isSuccessful) {
                        navController.navigate(R.id.action_registerFragment1_to_multiUserFragment1)
                        val userMap = HashMap<String, String>()
                        userMap["First Name"] = firstName
                        userMap["Last Name"] = lastName
                        userMap["Email Address"] = email
                        userMap["Temp"] = "password"
                        db.collection("users")
                            .add(userMap)
                            .addOnSuccessListener { documentReference ->
                                Log.d(TAG, "\"DocumentSnapshot added with ID: ${documentReference.id}")
                            }
                            .addOnFailureListener{ e ->
                                Log.w(TAG, "Error adding document", e)
                            }
                    }
                    else {
                        Toast.makeText(context, "" + task.exception?.message,
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