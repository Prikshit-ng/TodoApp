package com.todo.kotlin.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo_entity")
data class TodoEntity(
    val userId: String,
    @PrimaryKey val id: String,
    val title: String,
    val completed: Boolean
)