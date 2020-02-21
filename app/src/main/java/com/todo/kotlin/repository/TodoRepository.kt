package com.todo.kotlin.repository

import androidx.lifecycle.LiveData
import com.todo.kotlin.local.dao.TodoDAO
import com.todo.kotlin.local.model.TodoEntity
import com.todo.kotlin.remote.api.TodoService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import java.lang.Exception
import javax.inject.Inject

class TodoRepository @Inject constructor(private val networkService:TodoService, private val todoDAO: TodoDAO) {

    val todoListLiveData: LiveData<List<TodoEntity>> = todoDAO.todoListLiveData

    suspend fun getTodoList() {
        try {
            withTimeout(5_000) {
                networkService.getTodoList().let {
                    if(!it.isSuccessful){
                        throw DataFetchError("Server Error", Exception())
                    }
                    it.body()?:ArrayList()
                }
            }.map {
                TodoEntity(it.userId, it.id, it.title, it.completed)
            }.let {
                todoDAO.insertTodo(it)
            }
        } catch (error: Throwable) {
            throw DataFetchError("Unable to fetch data", error)
        }
    }

}
class DataFetchError(message: String, cause: Throwable) : Throwable(message, cause)

interface TodoCallback {
    fun onStart()
    fun onCompleted()
    fun onError(cause: Throwable)
}