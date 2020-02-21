package com.todo.kotlin.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.todo.kotlin.ui.MainActivity
import com.todo.kotlin.ui.MainViewModel
import com.todo.kotlin.ui.factory.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class UIModule {

    @Binds
    abstract fun bindsViewModelFactory(
        factory: ViewModelFactory
    ): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindsMainViewModel(mainVM: MainViewModel): ViewModel

    @ContributesAndroidInjector
    internal abstract fun contributesMainActivity(): MainActivity
}