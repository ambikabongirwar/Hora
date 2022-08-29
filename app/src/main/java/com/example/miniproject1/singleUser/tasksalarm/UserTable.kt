package com.example.miniproject1.singleUser.tasksalarm

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "UserTable")
data class UserTable (
    var title: String,
    var description: String,
    var category:String,
    var date:Long,
    var time:Long,
    var isFinished:Int= 0,
    var isShow:Int=0,

    @PrimaryKey(autoGenerate = true)
    var id : Long = 0
)