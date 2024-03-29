package com.example.miniproject1.multiUser.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.miniproject1.GroupActivity
import com.example.miniproject1.R
import com.example.miniproject1.multiUser.model.NamesViewModel

class GroupsAdapter(private val memberList: List<NamesViewModel>, val listener: GroupActivity) : RecyclerView.Adapter<GroupsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.group_card_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d("GroupActivity", "Inside Group Adapter: " + memberList[position].name)
        holder.bind(memberList[position].name, memberList[position].email, memberList[position].delete, listener)
    }

    override fun getItemCount(): Int {
        return memberList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.textView)
        val deleteBtn: ImageButton = itemView.findViewById(R.id.ibDelete)
        fun bind(name: String, memberEmail: String, delete: Boolean, listener: GroupActivity) {
            textView.text = name
            textView.setOnClickListener{itemView -> listener.onClicked(memberEmail)}
            deleteBtn.setOnClickListener{itemView -> listener.onDeleteClicked(memberEmail)}
        }
    }

}