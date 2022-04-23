package com.example.miniproject1.multiUser

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.miniproject1.R
import com.example.miniproject1.multiUser.home.GroupCardAdapter
import com.example.miniproject1.multiUser.model.Group
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MultiUserFragment1 : Fragment() {

    private lateinit var navController: NavController
    private lateinit var mAuth: FirebaseAuth
    lateinit var db: FirebaseFirestore

    private lateinit var recyclerView: RecyclerView
    private lateinit var groupArrayList: ArrayList<Group>
    private lateinit var groupCardAdapter: GroupCardAdapter
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

        Log.d(TAG, "onViewCreated")
        val logOutBtn: ImageButton = view.findViewById(R.id.ibLogOut)
        logOutBtn.setOnClickListener {
            FirebaseAuth.getInstance().signOut();
            navController.navigate(R.id.action_multiUserFragment1_to_registerFragment1)
        }

        recyclerView = view.findViewById(R.id.rvGroups)
        groupArrayList = arrayListOf()
        groupCardAdapter = GroupCardAdapter(groupArrayList)
        recyclerView.adapter = groupCardAdapter

        eventChangeListener()
    }

    private fun eventChangeListener() {
        db = FirebaseFirestore.getInstance()
        db.collection("groups")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    groupArrayList.add(Group(document.data["Name"].toString()))
                    groupCardAdapter.notifyDataSetChanged()
                    Log.d(TAG, "DocumentSnapshot data: ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.w("MultiUser Fragment", "Error getting documents.", exception)
            }
    }


    /*fun getData(): ArrayList<Group> {
        val db = Firebase.firestore
        var groupArrayList: ArrayList<Group> = arrayListOf<Group>()
        db.collection("groups")
            .get()
            .addOnSuccessListener { result ->
                lateinit var eachElement: Group
                for (document in result) {
                    eachElement = Group(document.data["Name"].toString())
                    groupArrayList.add(eachElement)
                    Log.d(TAG, "after adding each element: "+groupArrayList)
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents.", exception)
            }

        Log.d(TAG, "In datasource"+groupArrayList)
        return groupArrayList
    }*/
}