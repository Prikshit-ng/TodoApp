package com.todo.kotlin.remote.api

import com.todo.kotlin.remote.model.TodoItem
import retrofit2.Response
import retrofit2.http.GET

interface TodoService {
    @GET("todos")
    suspend fun getTodoList(): Response<List<TodoItem>>
}