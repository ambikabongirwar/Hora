package com.example.miniproject1.multiUser.datasource

import android.content.Context
import android.util.Log
import com.example.miniproject1.multiUser.model.GroupsPageViewModel
import com.google.firebase.firestore.FirebaseFirestore


data class GroupNamesDatasource(val context: Context) {
    val TAG = GroupNamesDatasource::class.simpleName
    val result: ArrayList<GroupsPageViewModel> = ArrayList()

    fun getDataFromFirebase(data: ArrayList<GroupsPageViewModel>) {
        val db = FirebaseFirestore.getInstance()
        Log.d(TAG, "Inside data from firebase")
        db.collection("groups")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    data.add(GroupsPageViewModel(""+document.data["Name"]))
                    Log.d(TAG, "${document.id} => ${document.data["Name"]}")
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
    }

    fun getGroupNamesList(): ArrayList<GroupsPageViewModel> {
        val db = FirebaseFirestore.getInstance()
        for (i in 1..10) {
            result.add(GroupsPageViewModel("Group " + i))
        }
        return result
    }
}
