package com.todo.kotlin.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.todo.kotlin.local.model.TodoEntity

@Dao
interface TodoDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodo(todoList: List<TodoEntity>)

    @get:Query("select * from todo_entity")
    val todoListLiveData: LiveData<List<TodoEntity>>
}