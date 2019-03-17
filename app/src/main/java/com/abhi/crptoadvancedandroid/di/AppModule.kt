package com.abhi.crptoadvancedandroid.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(val app: Application) {

    @Provides
    @Singleton
    fun provideApplicationContext(): Application = app

    @Provides
    @Singleton
    fun provideContext(app: Application): Context = app.applicationContext

}