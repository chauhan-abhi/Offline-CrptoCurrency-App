package com.abhi.crptoadvancedandroid.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.abhi.crptoadvancedandroid.data.Cryptocurrency

@Database(entities = [Cryptocurrency::class], version = 1)
abstract class CryptoDb: RoomDatabase() {
    abstract fun cryptocurrencyDao(): CryptocurrenciesDao
}