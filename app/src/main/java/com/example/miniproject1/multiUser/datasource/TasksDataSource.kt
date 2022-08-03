package com.example.miniproject1.multiUser.datasource

import com.example.miniproject1.EachMemberActivity
import com.example.miniproject1.GroupActivity
import com.example.miniproject1.multiUser.model.TaskModel

data class TasksDataSource(val tasks: ArrayList<TaskModel>){
    fun getAllTasks() {
        tasks.add(TaskModel("Task1", false))
        tasks.add(TaskModel("Task2", true))
        tasks.add(TaskModel("Task3", true))
        tasks.add(TaskModel("Task4", false))
        tasks.add(TaskModel("Task5", true))
        tasks.add(TaskModel("Task6", true))
        tasks.add(TaskModel("Task7", true))
    }
}
