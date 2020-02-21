package com.todo.kotlin.di

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class TodoApplication: DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerTodoAppComponent.builder().application(this).build()
    }
}