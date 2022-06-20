package com.example.miniproject1.multiUser.datasource

import com.example.miniproject1.GroupActivity
import com.example.miniproject1.multiUser.model.MembersAndTasksModel
import com.google.firebase.database.core.Context

data class MembersAndTasksDataSource(val context: GroupActivity) {
    val membersAndTasks: ArrayList<MembersAndTasksModel> = ArrayList()
    fun getAllMembersAndTasks(): ArrayList <MembersAndTasksModel> {
        //val tasks = TasksDataSource(context).getAllTasks()
        membersAndTasks.add(MembersAndTasksModel("m1@gmail.com"))
        membersAndTasks.add(MembersAndTasksModel("m2@gmail.com"))
        membersAndTasks.add(MembersAndTasksModel("m3@gmail.com"))
        return membersAndTasks
    }
}
