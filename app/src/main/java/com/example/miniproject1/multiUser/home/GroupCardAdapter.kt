package com.example.miniproject1.multiUser.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.miniproject1.R
import java.security.acl.Group

class GroupCardAdapter(private val groupList: ArrayList<Group>) :
    RecyclerView.Adapter<GroupCardAdapter.myViewHolder>(){

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener) {
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupCardAdapter.myViewHolder {
        val buttonView = LayoutInflater.from(parent.context).inflate(R.layout.group_button_item, parent, false)
        return myViewHolder(buttonView, mListener)
    }

    override fun onBindViewHolder(holder: GroupCardAdapter.myViewHolder, position: Int) {
        val group: Group = groupList[position]
        holder.groupName.text = group.name
    }

    override fun getItemCount(): Int {
        return groupList.size
    }

    class myViewHolder(buttonView: View, listener: onItemClickListener): RecyclerView.ViewHolder(buttonView) {
        val groupName: TextView = buttonView.findViewById(R.id.groupNameBtn)

        init {
            buttonView.setOnClickListener{
                listener.onItemClick(adapterPosition)
            }
        }
    }

}