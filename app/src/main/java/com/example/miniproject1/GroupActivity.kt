package com.example.miniproject1

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.miniproject1.multiUser.GroupsAdapter
import com.example.miniproject1.multiUser.datasource.MembersAndTasksDataSource

class GroupActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group)

        val groupName = findViewById<TextView>(R.id.groupNametv)
        groupName.text = intent.getStringExtra("groupName")

        val recyclerview = findViewById<RecyclerView>(R.id.rvGroups)
        recyclerview.layoutManager = LinearLayoutManager(this)

        val groupNames = MembersAndTasksDataSource(this).getAllMembersAndTasks()

        val adapter = GroupsAdapter(groupNames)

        recyclerview.adapter = adapter
    }


}