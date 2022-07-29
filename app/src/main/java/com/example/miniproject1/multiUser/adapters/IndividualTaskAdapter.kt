package com.example.miniproject1.multiUser.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.miniproject1.R
import com.example.miniproject1.multiUser.model.TaskModel

class IndividualTaskAdapter(private val mList: List<TaskModel>) : RecyclerView.Adapter<IndividualTaskAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.individual_task_item, parent, false)

        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mList[position].task, mList[position].status)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val textView: TextView = itemView.findViewById(R.id.textView)
        val statusCB: CheckBox = itemView.findViewById(R.id.checkBox2)

        fun bind(taskName: String, status: Boolean) {
            textView.text = taskName
            statusCB.isChecked = status
        }
    }
}