package com.abhi.crptoadvancedandroid.di

import android.app.Application
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidInjectionModule::class, BuildersModule::class, AppModule::class])
interface AppComponent {
    fun inject(app: Application)
}