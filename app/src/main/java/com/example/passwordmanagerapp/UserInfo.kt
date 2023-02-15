package com.example.passwordmanagerapp

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "UserInfo")
data class UserInfo(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "Id")
    var id:Int,
    @ColumnInfo(name = "Username")
    var username:String,
    @ColumnInfo(name = "email")
    var email:String,
    @ColumnInfo(name = "Password")
    var password:String

)