package com.example.miniproject1.multiUser.model

data class Group(
    val Name: String,
    val Participants: ArrayList<MembersAndTasksModel> ?= null
)