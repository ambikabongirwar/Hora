package com.example.miniproject1.multiUser.datasource

import android.content.Context
import android.util.Log
import com.example.miniproject1.multiUser.model.ItemsViewModel
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


data class GroupNamesDatasource(val context: Context) {
    val TAG = GroupNamesDatasource::class.simpleName

    fun getDataFromFirebase() {
        /*db.collection("groups")
            .get()
            .addOnSuccessListener { result ->
                val data = ArrayList<ItemsViewModel>()
                for (document in result) {
                    data.add(ItemsViewModel(""+document.data["Name"]))
                    Log.d(TAG, "${document.id} => ${document.data["Name"]}")
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }*/
    }

    fun getGroupNamesList(): ArrayList<ItemsViewModel> {
        val db = FirebaseFirestore.getInstance()
        val result: ArrayList<ItemsViewModel> = ArrayList()
        for (i in 1..10) {
            result.add(ItemsViewModel("Group " + i))
        }
        return result
    }
}
