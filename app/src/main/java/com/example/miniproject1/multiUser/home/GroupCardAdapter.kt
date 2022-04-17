package com.example.miniproject1.multiUser.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.miniproject1.R
import java.security.acl.Group

class GroupCardAdapter(private val groupList: ArrayList<Groups>) : RecyclerView.Adapter<GroupCardAdapter.myViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupCardAdapter.myViewHolder {
        val buttonView = LayoutInflater.from(parent.context).inflate(R.layout.group_button_item, parent, false)
        return myViewHolder(buttonView)
    }

    override fun onBindViewHolder(holder: GroupCardAdapter.myViewHolder, position: Int) {
        val group: Groups = groupList[position]
        holder.groupName.text = group.name
    }

    override fun getItemCount(): Int {
        return groupList.size
    }

    public class myViewHolder(buttonView: View): RecyclerView.ViewHolder(buttonView) {
        val groupName: TextView = buttonView.findViewById(R.id.groupNameBtn)
    }

}