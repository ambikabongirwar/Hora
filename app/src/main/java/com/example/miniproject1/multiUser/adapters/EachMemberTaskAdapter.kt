package com.example.miniproject1.multiUser.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.miniproject1.R
import com.example.miniproject1.multiUser.model.TaskModel

class EachMemberTaskAdapter(private val mList: List<TaskModel>) : RecyclerView.Adapter<EachMemberTaskAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EachMemberTaskAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.individual_task_item, parent, false)

        return EachMemberTaskAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: EachMemberTaskAdapter.ViewHolder, position: Int) {
        holder.bind(mList[position].task, mList[position].status)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val taskTv: TextView = itemView.findViewById(R.id.textView)
        val statusCb: CheckBox = itemView.findViewById(R.id.checkBox2)
        fun bind(task: String, status: Boolean) {
            taskTv.text = task
            statusCb.isChecked = status
        }
    }
}