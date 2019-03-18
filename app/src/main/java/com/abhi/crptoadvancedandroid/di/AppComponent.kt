package com.abhi.crptoadvancedandroid.di

import android.app.Application
import com.abhi.crptoadvancedandroid.di.module.AppModule
import com.abhi.crptoadvancedandroid.di.module.BuildersModule
import com.abhi.crptoadvancedandroid.di.module.NetModule
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidInjectionModule::class, BuildersModule::class, AppModule::class, NetModule::class])
interface AppComponent {
    fun inject(app: Application)
}