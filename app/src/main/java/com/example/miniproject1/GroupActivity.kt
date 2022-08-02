package com.example.miniproject1

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.miniproject1.multiUser.ItemListener
import com.example.miniproject1.multiUser.adapters.GroupsAdapter
import com.example.miniproject1.multiUser.datasource.MembersAndTasksDataSource
import com.example.miniproject1.multiUser.model.ItemsViewModel
import com.example.miniproject1.multiUser.model.MembersAndTasksModel

class GroupActivity : AppCompatActivity(), ItemListener {

    private lateinit var membersArrayList: ArrayList<ItemsViewModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group)

        val groupName = findViewById<TextView>(R.id.groupNametv)
        groupName.text = intent.getStringExtra("groupName")

        val recyclerview = findViewById<RecyclerView>(R.id.rvGroups)
        recyclerview.layoutManager = LinearLayoutManager(this)

        val groupNames = MembersAndTasksDataSource(this).getAllMembersAndTasks()
        membersArrayList = ArrayList()
        membersArrayList.add(ItemsViewModel("m1@gmail.com"))
        membersArrayList.add(ItemsViewModel("m2@gmail.com"))
        membersArrayList.add(ItemsViewModel("m3@gmail.com"))

        val adapter = GroupsAdapter(membersArrayList, this)

        recyclerview.adapter = adapter
    }

    override fun onClicked(emailId: String) {
        val intent = Intent(this, EachMemberActivity::class.java)
        intent.putExtra("groupName", intent.getStringExtra("groupName"))
        intent.putExtra("emailId", emailId)
        this?.startActivity(intent)
        //Toast.makeText(context, "Name is "+name, Toast.LENGTH_SHORT).show()
    }

}