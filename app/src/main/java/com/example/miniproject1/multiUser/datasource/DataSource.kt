package com.example.miniproject1.multiUser.datasource

import android.util.Log
import com.example.miniproject1.R
import com.example.miniproject1.multiUser.model.Group
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class DataSource {
    val TAG = "MultiUserFragment1"
    var groupArrayList: ArrayList<Group> = arrayListOf<Group>()
    fun loadGroupNames(): ArrayList<Group> {
        val db = Firebase.firestore
        //var groupArrayList: ArrayList<Group> = arrayListOf<Group>()
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
    }
}