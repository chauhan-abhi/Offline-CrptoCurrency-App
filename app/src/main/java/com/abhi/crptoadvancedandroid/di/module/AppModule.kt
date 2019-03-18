package com.abhi.crptoadvancedandroid.di.module

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
    fun providesApplicationContext(): Application = app

    @Provides
    @Singleton
    fun providesContext(app: Application): Context = app.applicationContext

    @Provides
    @Singleton
    fun providesCryptocurrenciesDatabase(app: Application): CryptoDb = Room.databaseBuilder(app,
        CryptoDb::class.java, Constants.DATABASE_NAME)
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun providesCrptocurrenciesDao(database: CryptoDb): CryptocurrenciesDao = database.cryptocurrencyDao()
}