package com.example.miniproject1.multiUser.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.miniproject1.GroupActivity
import com.example.miniproject1.R
import com.example.miniproject1.multiUser.model.ItemsViewModel

class GroupsAdapter(private val mList: List<ItemsViewModel>, val listener: GroupActivity) : RecyclerView.Adapter<GroupsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.group_card_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d("GroupActivity", "Inside Group Adapter: " + mList[position].text)
        holder.bind(mList[position].text, listener)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.textView)
        fun bind(memberEmail: String, listener: GroupActivity) {
            textView.text = memberEmail
            textView.setOnClickListener{itemView -> listener.onClicked(memberEmail)}
        }
    }

}