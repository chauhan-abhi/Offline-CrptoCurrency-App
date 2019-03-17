package com.abhi.crptoadvancedandroid.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.abhi.crptoadvancedandroid.data.source.local.CryptoDb
import com.abhi.crptoadvancedandroid.data.source.local.CryptocurrenciesDao
import com.abhi.crptoadvancedandroid.utils.Constants
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

    @Provides
    @Singleton
    fun provideCryptocurrenciesDatabase(app: Application): CryptoDb = Room.databaseBuilder(app,
        CryptoDb::class.java, Constants.DATABASE_NAME)
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun provideCrptocurrenciesDao(database: CryptoDb): CryptocurrenciesDao = database.cryptocurrencyDao()
}