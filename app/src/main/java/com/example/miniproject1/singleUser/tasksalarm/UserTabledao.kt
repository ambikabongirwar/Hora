package com.example.miniproject1.singleUser.tasksalarm

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserTabledao {

    @Insert
    fun insertTask(user : UserTable): Long

    @Query("SELECT * FROM UserTable where isFinished == 0")
    fun getTask(): LiveData<List<UserTable>>

    @Query("Update UserTable Set isFinished = 1 where id=:uid")
    fun FinishTask(uid:Long)

    @Query("UPDATE UserTable Set isShow = :isShow  where id LIKE :id")
    fun isShownUpdate(id:Long , isShow : Int)

    @Query("SELECT * from UserTable where id Like :id")
    fun get(id : Long): UserTable

    @Query("Delete from UserTable  where id=:uid")
    fun deleteTask(uid:Long)
}