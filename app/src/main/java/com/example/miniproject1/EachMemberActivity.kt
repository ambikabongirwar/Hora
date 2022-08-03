package com.example.miniproject1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class EachMemberActivity : AppCompatActivity() {

    lateinit var groupName: String
    lateinit var memberEmail: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_each_member)

        groupName = intent.getStringExtra("groupName").toString()
        memberEmail = intent.getStringExtra("emailId").toString()

        val memberTv = findViewById<TextView>(R.id.memberEmailtv)
        memberTv.text = memberEmail
    }
}