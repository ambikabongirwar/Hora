package com.example.miniproject1.multiUser.datasource

import com.example.miniproject1.multiUser.model.ItemsViewModel
import com.example.miniproject1.multiUser.model.TaskModel
import com.google.firebase.database.core.Context

data class TasksDataSource(val context: Context){
    val tasks: ArrayList<TaskModel> = ArrayList()
    fun getAllTasks(): ArrayList <TaskModel> {
        tasks.add(TaskModel("Task1", false))
        tasks.add(TaskModel("Task2", true))
        tasks.add(TaskModel("Task3", true))
        return tasks;
    }
}
