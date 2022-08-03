package com.example.miniproject1.multiUser.datasource

import com.example.miniproject1.GroupActivity
import com.example.miniproject1.multiUser.model.MembersAndTasksModel
import com.example.miniproject1.multiUser.model.TaskModel

data class MembersAndTasksDataSource(val context: GroupActivity) {
    val membersAndTasks: ArrayList<MembersAndTasksModel> = ArrayList()
    val tasksList: ArrayList<TaskModel> = ArrayList()
    fun getAllMembersAndTasks(): ArrayList <MembersAndTasksModel> {
        TasksDataSource(tasksList).getAllTasks()
        membersAndTasks.add(MembersAndTasksModel("m1@gmail.com", tasksList))
        membersAndTasks.add(MembersAndTasksModel("m2@gmail.com", tasksList))
        membersAndTasks.add(MembersAndTasksModel("m3@gmail.com", tasksList))
        return membersAndTasks
    }
}
