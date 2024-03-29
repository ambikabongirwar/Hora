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
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RegisterFragment : Fragment(), View.OnClickListener {

    lateinit var navController: NavController
    private lateinit var mAuth: FirebaseAuth
    lateinit var email_id_edt: EditText
    lateinit var password_edt: EditText
    lateinit var confirm_password_edt: EditText
    lateinit var db: FirebaseFirestore
    lateinit var firstName_edt: EditText
    lateinit var lastName_edt: EditText
    val TAG = "RegisterFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_register, container, false)
        mAuth = FirebaseAuth.getInstance()
        db = Firebase.firestore
        Log.d(TAG, "onCreateView")
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "On View Created")
        firstName_edt = view.findViewById(R.id.editTextRegisterFirstName)
        lastName_edt = view.findViewById(R.id.editTextRegisterLastName)
        email_id_edt = view.findViewById(R.id.editTextRegisterEmailAddress)
        password_edt = view.findViewById(R.id.editTextRegisterPassword)
        confirm_password_edt = view.findViewById(R.id.editTextRegisterConfirmPassword)
        navController = Navigation.findNavController(view)
        view.findViewById<Button>(R.id.btnRegisterToLogin).setOnClickListener(this)
        Log.d(TAG, "After register to login button in on view created")
        view.findViewById<Button>(R.id.btnRegister).setOnClickListener(this)
        Log.d(TAG, "After register in on view created")
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

    override fun onClick(v: View?) {
        Log.d(TAG, "Inside On click")
        when(v!!.id) {
            R.id.btnRegister -> register()
            R.id.btnRegisterToLogin -> navController.navigate(R.id.action_registerFragment_to_loginFragment)
        }
    }

    fun register(){
        //Log.d(TAG, "inside register function")
        val firstName = firstName_edt.text.toString()
        val lastName = lastName_edt.text.toString()
        val email = email_id_edt.text.toString()
        //Log.d(TAG, "got email")
        val password = password_edt.text.toString()
        //Log.d(TAG, "got password")
        val confirmPassword = confirm_password_edt.text.toString()
        //Log.d(TAG, "input email and pasword")
        if (password == confirmPassword) {
            mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity()) {task ->
                    if(task.isSuccessful) {
                        navController.navigate(R.id.action_registerFragment_to_multiUserFragment)
                    }
                    else {
                        Toast.makeText(context, "" + task.exception?.message,
                            Toast.LENGTH_SHORT).show()
                    }
                }

            val userMap = HashMap<String, String>()
            userMap["First Name"] = firstName
            userMap["Last Name"] = lastName
            userMap["Email Address"] = email
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
            Toast.makeText(context, "Password and Confirm Password do not match",
                Toast.LENGTH_SHORT).show()
        }
    }
}