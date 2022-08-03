package com.example.miniproject1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.miniproject1.multiUser.adapters.EachMemberTaskAdapter
import com.example.miniproject1.multiUser.datasource.TasksDataSource
import com.example.miniproject1.multiUser.model.ItemsViewModel
import com.example.miniproject1.multiUser.model.TaskModel
import com.google.firebase.firestore.FirebaseFirestore

class EachMemberActivity : AppCompatActivity() {

    lateinit var db: FirebaseFirestore

    lateinit var groupName: String
    lateinit var memberEmail: String
    lateinit var taskList: ArrayList<TaskModel>
    lateinit var adapter: EachMemberTaskAdapter

    var TAG = "EachMemberActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_each_member)

        groupName = intent.getStringExtra("groupName").toString()
        memberEmail = intent.getStringExtra("emailId").toString()

        val memberTv = findViewById<TextView>(R.id.memberEmailtv)
        memberTv.text = memberEmail

        val recyclerview = findViewById<RecyclerView>(R.id.rvTasks)
        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.setHasFixedSize(true)

        taskList = ArrayList()

        adapter = EachMemberTaskAdapter(taskList)

        recyclerview.adapter = adapter
        eventChangeListener()
    }

    private fun eventChangeListener() {
        TasksDataSource(taskList).getAllTasks()
        db = FirebaseFirestore.getInstance()
        db.collection("groups")
            .whereEqualTo("groupName", groupName)
            .whereEqualTo("emailId", memberEmail)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d(TAG, "" + document.data)
                    /*
                    var participants = document.data["participants"].toString()
                    participants = participants.slice(1..participants.length - 2)
                    Log.d(TAG, "DocumentSnapshot Particpants data: ${participants}")
                    for (member in participants.split(",")) {
                        membersArrayList.add(ItemsViewModel(member))
                    }
                    adapter.notifyDataSetChanged()*/
                }
            }
            .addOnFailureListener { exception ->
                Log.w("Group Activity Fragment", "Error getting documents.", exception)
            }
    }
}