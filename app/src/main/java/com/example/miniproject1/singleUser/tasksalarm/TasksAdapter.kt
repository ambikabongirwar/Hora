package com.example.miniproject1.singleUser.tasksalarm

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.miniproject1.R

class TasksAdapter(
    private val context: TasksFragment,
    private val tasks: List<Task>
): RecyclerView.Adapter <TasksAdapter.TaskViewHolder>(){

    class TaskViewHolder(private val itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemTitle: TextView = itemView.findViewById(R.id.taskTitle)
        val addButton: CheckBox = itemView.findViewById(R.id.checkBoxDone)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.itemTitle.text = tasks[position].title
        holder.addButton.isChecked = tasks[position].isChecked
    }

    override fun getItemCount(): Int {
        return tasks.size
    }
}