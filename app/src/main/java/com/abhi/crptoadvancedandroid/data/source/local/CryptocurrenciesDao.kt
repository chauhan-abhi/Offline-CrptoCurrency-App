package com.abhi.crptoadvancedandroid.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.abhi.crptoadvancedandroid.data.Cryptocurrency
import io.reactivex.Single

@Dao
interface CryptocurrenciesDao {

    @Query("SELECT * FROM cryptocurrencies ORDER BY rank limit :limit offset :offset")
    fun queryCryptocurrencies(limit: Int, offset: Int): Single<Cryptocurrency>

    @Insert(
        onConflict = OnConflictStrategy.REPLACE
    )
    fun insertCryptocurrency(cryptocurrency: Cryptocurrency)
}