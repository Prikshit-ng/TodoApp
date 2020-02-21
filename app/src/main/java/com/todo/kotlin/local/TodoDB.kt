package com.todo.kotlin.local

import android.content.Context
import androidx.annotation.NonNull
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.todo.kotlin.local.dao.TodoDAO
import com.todo.kotlin.local.model.TodoEntity

@Database(
    entities = [TodoEntity::class],
    version = 1,
    exportSchema = false
)
abstract class TodoDB : RoomDatabase() {
    companion object {
        private val LOCK = Any()
        private const val DATABASE_NAME = "todo_ng.db"
        @Volatile
        private var INSTANCE: TodoDB? = null

        fun getInstance(@NonNull context: Context): TodoDB {
            if (INSTANCE == null) {
                synchronized(LOCK) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context,
                            TodoDB::class.java,
                            DATABASE_NAME
                        ).build()
                    }
                }
            }
            return INSTANCE!!
        }
    }

    abstract fun getTodoDao(): TodoDAO
}