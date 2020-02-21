package com.todo.kotlin.di

import com.todo.kotlin.BuildConfig
import com.todo.kotlin.remote.api.TodoService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
class NetworkModule {
    @Provides
    fun providesDeliveryService(retrofit: Retrofit): TodoService =
        retrofit.create(TodoService::class.java)


    @Provides
    fun providesRetrofit(): Retrofit {
        val okHttpClient = OkHttpClient.Builder()
            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .build()
        return Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.SERVER_URL)
            .build()
    }
}