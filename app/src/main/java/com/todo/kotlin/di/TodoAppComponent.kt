package com.todo.kotlin.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidInjectionModule::class,
    NetworkModule::class,
    LocalPersistenceModule::class,
    UIModule::class,
    AppModule::class
])
interface TodoAppComponent: AndroidInjector<TodoApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(app: Application): Builder

        fun build(): TodoAppComponent
    }
    override fun inject(app: TodoApplication)
}