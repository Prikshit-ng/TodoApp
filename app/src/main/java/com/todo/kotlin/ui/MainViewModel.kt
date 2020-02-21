package com.todo.kotlin.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.todo.kotlin.repository.TodoCallback
import com.todo.kotlin.repository.TodoRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(private val repository: TodoRepository) : ViewModel() {

    val todoList = repository.todoListLiveData

    val responseStatus = MutableLiveData<Status>()

    fun fetchTodoList(){
        fetchTodoList(object :TodoCallback{
            override fun onStart() {
                responseStatus.postValue(Status.LOADING)
            }

            override fun onCompleted() {
                responseStatus.postValue(Status.COMPLETED)
            }

            override fun onError(cause: Throwable) {
                responseStatus.postValue(Status.ERROR)
            }
        })
    }
    private fun fetchTodoList(todoCallback: TodoCallback) {
        todoCallback.onStart()
        viewModelScope.launch {
            try {
                repository.getTodoList()
                todoCallback.onCompleted()
            } catch (throwable: Throwable) {
                todoCallback.onError(throwable)
            }
        }
    }

    enum class Status{
        LOADING,
        COMPLETED,
        ERROR
    }
}