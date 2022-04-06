package com.example.miniproject1.singleUser.tasks

class DataSource {
    val taskList = mutableListOf<Task>(
        Task("Complete Seminar", true),
        Task("Complete mini project", false),
        Task("Get stitches removed", false),
        Task("Get root canal", false)
    )
    fun loadTasks(): List<Task> {
        return taskList
    }
    fun addTasks(title: String, isChecked: Boolean) {
        val task = Task(title, isChecked)
        taskList.add(task)
    }
}