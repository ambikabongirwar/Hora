package com.example.miniproject1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.miniproject1.multiUser.ItemListener
import com.example.miniproject1.multiUser.adapters.GroupsAdapter
import com.example.miniproject1.multiUser.model.ItemsViewModel
import com.google.firebase.firestore.FirebaseFirestore

class GroupActivity : AppCompatActivity(), ItemListener {

    private lateinit var membersArrayList: ArrayList<ItemsViewModel>
    lateinit var db: FirebaseFirestore

    lateinit var groupName: String
    private lateinit var adapter: GroupsAdapter
    var TAG = "GroupActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group)

        groupName = intent.getStringExtra("groupName").toString()
        val groupNameTv = findViewById<TextView>(R.id.groupNametv)
        groupNameTv.text = groupName

        val recyclerview = findViewById<RecyclerView>(R.id.rvGroups)
        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.setHasFixedSize(true)

        membersArrayList = ArrayList()

        adapter = GroupsAdapter(membersArrayList, this)

        recyclerview.adapter = adapter
        eventChangeListener()
    }

    override fun onClicked(emailId: String) {
        val intent = Intent(this, EachMemberActivity::class.java)
        Log.d(TAG, "Clicked on a member email id: " + emailId)
        intent.putExtra("groupName", groupName)
        intent.putExtra("emailId", emailId)
        startActivity(intent)
    }

    private fun eventChangeListener() {
        db = FirebaseFirestore.getInstance()
        db.collection("groups")
            .whereEqualTo("name", groupName)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    var participants = document.data["participants"].toString()
                    participants = participants.slice(1..participants.length - 2)
                    Log.d(TAG, "DocumentSnapshot Particpants data: ${participants}")
                    for (member in participants.split(",")) {
                        membersArrayList.add(ItemsViewModel(member))
                    }
                    adapter.notifyDataSetChanged()
                }
            }
            .addOnFailureListener { exception ->
                Log.w("Group Activity Fragment", "Error getting documents.", exception)
            }
    }
}