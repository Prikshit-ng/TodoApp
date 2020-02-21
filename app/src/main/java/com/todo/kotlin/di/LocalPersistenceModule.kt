package com.todo.kotlin.di

import android.app.Application
import com.todo.kotlin.local.TodoDB
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class LocalPersistenceModule {
    @Provides
    @Singleton
    fun providesDatabase(
        application: Application
    ) = TodoDB.getInstance(application.applicationContext)

    @Provides
    @Singleton
    fun providesDeliveryDAO(
        todoDB: TodoDB
    ) = todoDB.getTodoDao()
}