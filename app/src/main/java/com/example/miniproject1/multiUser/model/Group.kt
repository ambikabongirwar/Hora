package com.example.miniproject1.multiUser.model

data class Group(
    val name: String,
    val participants: ArrayList<String> ?= null
)