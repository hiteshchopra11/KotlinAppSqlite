package com.example.loginapp.Model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// model class
data class User(val id: Int = -1, val name: String, val email: String, val password: String)
