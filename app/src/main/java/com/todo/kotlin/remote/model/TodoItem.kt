package com.todo.kotlin.remote.model

import com.google.gson.annotations.SerializedName

data class TodoItem(
    @SerializedName("userId")val userId: String,
    @SerializedName("id")val id: String,
    @SerializedName("title")val title: String,
    @SerializedName("completed")val completed: Boolean
)