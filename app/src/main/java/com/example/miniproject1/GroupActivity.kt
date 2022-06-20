package com.example.miniproject1

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class GroupActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group)

        val groupName = findViewById<TextView>(R.id.groupNametv)
        groupName.text = intent.getStringExtra("groupName")
    }


}