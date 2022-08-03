package com.example.miniproject1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.miniproject1.multiUser.ItemListener
import com.example.miniproject1.multiUser.adapters.EachMemberTaskAdapter
import com.example.miniproject1.multiUser.model.TaskModel
import com.google.firebase.firestore.FirebaseFirestore

class EachMemberActivity : AppCompatActivity(), ItemListener {

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

        adapter = EachMemberTaskAdapter(taskList, this)

        recyclerview.adapter = adapter
        getData()
        Log.d(TAG, "After calling getData function, inside onCreate, taskList: " + taskList)
    }

    fun getData() {
        db = FirebaseFirestore.getInstance()
        db.collection("members")
            .whereEqualTo("groupName", groupName)
            .whereEqualTo("emailId", memberEmail)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    var tasks = document.data["tasks"].toString()
                    tasks = tasks.slice(1..tasks.length - 2)
                    for (taskStatus in tasks.split(", ")) {
                        val taskAndStatus = taskStatus.split("=")
                        var status: Boolean
                        if (taskAndStatus[1] == "true")
                            status = true
                        else
                            status = false
                        taskList.add(TaskModel(taskAndStatus[0], status))
                    }
                }
                adapter.notifyDataSetChanged()
                Log.d(TAG, "Inside getData function, taskList: " + taskList)
            }
            .addOnFailureListener{ exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
    }

    override fun onClicked(position: String) {
        val pos: Int = position.toInt()
        //Toast.makeText(this, "Item at " + pos.toString() + " clicked", Toast.LENGTH_SHORT).show()
        db = FirebaseFirestore.getInstance()
        db.collection("members")
            .whereEqualTo("groupName", groupName)
            .whereEqualTo("emailId", memberEmail)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val taskName: String = "tasks." + taskList[pos].task
                    db.collection("members").document(document.id).update(mapOf(taskName to !taskList[pos].status))
                    Log.d(TAG, "Task Name: " + taskName + " Status: " + taskList[pos].status)
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
    }
}