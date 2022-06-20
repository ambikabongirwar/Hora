package com.example.miniproject1.multiUser

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.miniproject1.R
import com.example.miniproject1.multiUser.model.MembersAndTasksModel

class GroupsAdapter(private val mList: List<MembersAndTasksModel>) : RecyclerView.Adapter<GroupsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupsAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.member_card_item, parent, false)

        return GroupsAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: GroupsAdapter.ViewHolder, position: Int) {
        holder.bind(mList[position].emailId)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val membersNameTV: TextView = itemView.findViewById(R.id.memberNameTV)

        fun bind(memberName: String) {
            membersNameTV.text = memberName
        }
    }

}