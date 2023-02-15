package com.example.passwordmanagerapp

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface UserDao {
    @Insert
    suspend fun insertUser(userInfo: UserInfo)
    @Update
    suspend fun updateUser(userInfo: UserInfo)
    @Delete
    suspend fun deleteUser(userInfo: UserInfo)
    @Query("SELECT * FROM UserInfo")
    fun getAllUser():LiveData<List<UserInfo>>
}