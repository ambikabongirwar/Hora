package com.example.miniproject1.multiUser.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.miniproject1.GroupActivity
import com.example.miniproject1.R
import com.example.miniproject1.multiUser.ItemListener
import com.example.miniproject1.multiUser.datasource.TasksDataSource
import com.example.miniproject1.multiUser.model.MembersAndTasksModel
import com.example.miniproject1.multiUser.model.TaskModel

class GroupsAdapter(private val mList: List<MembersAndTasksModel>, val listener: GroupActivity) : RecyclerView.Adapter<GroupsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.member_card_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mList[position].emailId, mList[position].tasks)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val membersNameTV: TextView = itemView.findViewById(R.id.memberNameTV)
        val rv: RecyclerView = itemView.findViewById(R.id.rvTasks)

        fun bind(memberName: String, tasks: ArrayList<TaskModel>) {
            membersNameTV.text = memberName
            rv.layoutManager = LinearLayoutManager(itemView.context)
            val tasks = TasksDataSource(itemView.context as GroupActivity).getAllTasks()
            val adapter = IndividualTaskAdapter(tasks)
            rv.adapter = adapter

        }
    }

}