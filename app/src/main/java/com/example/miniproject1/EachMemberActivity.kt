package com.example.miniproject1

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.miniproject1.multiUser.ItemListener
import com.example.miniproject1.multiUser.adapters.EachMemberTaskAdapter
import com.example.miniproject1.multiUser.model.TaskModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import java.text.FieldPosition

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

        val newTaskBtn: FloatingActionButton = findViewById(R.id.addTaskBtn)
        newTaskBtn.setOnClickListener{
            val getTaskNameDialogView = LayoutInflater.from(this).inflate(R.layout.simple_et,null)
            val builder = AlertDialog.Builder(this).setView(getTaskNameDialogView).setTitle("Enter task: ")
            val alertDialog = builder.show()
            getTaskNameDialogView.findViewById<Button>(R.id.createGroupWithNameBtn).setOnClickListener {
                val task = getTaskNameDialogView.findViewById<EditText>(R.id.etNewGroupName).text.toString()
                addTask(task);
                alertDialog.dismiss()
            }
        }
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
                    Log.d(TAG, ""+tasks)
                    if (tasks != "") {
                        for (taskStatus in tasks.split(", ")) {
                            var ts = taskStatus.slice(1..taskStatus.length - 2)
                            val taskAndStatus = ts.split("=")
                            var status: Boolean = taskAndStatus[1] == "true"
                            taskList.add(TaskModel(taskAndStatus[0], status))
                        }
                        adapter.notifyDataSetChanged()
                    }
                }
                Log.d(TAG, "Inside getData function, taskList: " + taskList)
            }
            .addOnFailureListener{ exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
    }

    override fun onClicked(task: String) {
        //Toast.makeText(this, "Item at " + pos.toString() + " clicked", Toast.LENGTH_SHORT).show()
        db = FirebaseFirestore.getInstance()
        var taskListTemp : ArrayList <HashMap<String, Boolean>> = ArrayList()
        db.collection("members")
            .whereEqualTo("groupName", groupName)
            .whereEqualTo("emailId", memberEmail)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    var tasks = document.data["tasks"].toString()
                    tasks = tasks.slice(1..tasks.length - 2)
                    for (taskStatus in tasks.split(", ")) {
                        var ts = taskStatus.slice(1..taskStatus.length - 2)
                        val taskAndStatus = ts.split("=")
                        var status: Boolean = false
                        if (taskAndStatus[0] == task) {
                            status = !(taskAndStatus[1] == "true")
                        }
                        else {
                            status = taskAndStatus[1] == "true"
                        }
                        var hm: HashMap<String, Boolean> = HashMap()
                        hm[taskAndStatus[0]] = status
                        taskListTemp.add(hm)
                    }
                    db.collection("members").document(document.id)
                        .update("tasks", taskListTemp)
                        .addOnSuccessListener {
                            Log.d(TAG, "DocumentSnapshot successfully updated!")
                        }
                        .addOnFailureListener { e ->
                            Log.w(TAG, "Error updating document", e)
                        }
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
    }

    fun onDeleteClicked(task: String, position: Int) {
        db = FirebaseFirestore.getInstance()
        var taskListTemp : ArrayList <HashMap<String, Boolean>> = ArrayList()
        db.collection("members")
            .whereEqualTo("groupName", groupName)
            .whereEqualTo("emailId", memberEmail)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    var tasks = document.data["tasks"].toString()
                    tasks = tasks.slice(1..tasks.length - 2)
                    for (taskStatus in tasks.split(", ")) {
                        var ts = taskStatus.slice(1..taskStatus.length - 2)
                        val taskAndStatus = ts.split("=")
                        var status: Boolean = false
                        if (taskAndStatus[0] == task) {
                            continue
                        }
                        var hm: HashMap<String, Boolean> = HashMap()
                        hm[taskAndStatus[0]] = status
                        taskListTemp.add(hm)
                    }
                    db.collection("members").document(document.id)
                        .update("tasks", taskListTemp)
                        .addOnSuccessListener {
                            Log.d(TAG, "DocumentSnapshot successfully updated!")
                        }
                        .addOnFailureListener { e ->
                            Log.w(TAG, "Error updating document", e)
                        }
                    Toast.makeText(this, task + "Deleted", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
    }

    fun addTask(task: String) {
        db = FirebaseFirestore.getInstance()
        var taskListTemp : ArrayList <HashMap<String, Boolean>> = ArrayList()
        var documentExists: Boolean = false
        db.collection("members")
            .whereEqualTo("groupName", groupName)
            .whereEqualTo("emailId", memberEmail)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    //If document exists:
                    documentExists = true
                    var tasks = document.data["tasks"].toString()
                    tasks = tasks.slice(1..tasks.length - 2)
                    if (tasks != "") {
                        for (taskStatus in tasks.split(", ")) {
                            var ts = taskStatus.slice(1..taskStatus.length - 2)
                            val taskAndStatus = ts.split("=")
                            var status: Boolean = taskAndStatus[1] == "true"
                            var hm: HashMap<String, Boolean> = HashMap()
                            hm[taskAndStatus[0]] = status
                            taskListTemp.add(hm)
                        }
                    }
                    var hm : HashMap <String, Boolean> = HashMap()
                    hm[task] = false
                    taskListTemp.add(hm)
                    db.collection("members").document(document.id)
                        .update("tasks", taskListTemp)
                        .addOnSuccessListener {
                            Log.d(TAG, "DocumentSnapshot successfully updated!")
                        }
                        .addOnFailureListener { e ->
                            Log.w(TAG, "Error updating document", e)
                        }
                }
                if (!documentExists) {
                    val data = hashMapOf(
                        "emailId" to memberEmail,
                        "groupName" to groupName,
                        "tasks" to arrayListOf(hashMapOf(task to false))
                    )
                    db.collection("members")
                        .add(data)
                        .addOnSuccessListener { documentReference ->
                            Log.d(TAG, "DocumentSnapshot written with ID: ${documentReference.id}")
                        }
                        .addOnFailureListener { e ->
                            Log.w(TAG, "Error adding document", e)
                        }
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
    }
}