package com.abhi.crptoadvancedandroid.data.source

import android.util.Log
import com.abhi.crptoadvancedandroid.data.Cryptocurrency
import com.abhi.crptoadvancedandroid.data.source.local.CryptocurrenciesDao
import com.abhi.crptoadvancedandroid.data.source.remote.ApiInterface
import io.reactivex.Observable
import javax.inject.Inject

class CryptocurrencyRepository @Inject constructor(
    val apiInterface: ApiInterface,
    val cryptocurrenciesDao: CryptocurrenciesDao
) {
    fun getCryptocurrencies(): Observable<List<Cryptocurrency>> {
        val observableFromApi = getCryptocurrenciesFromApi()
        val observableFromDb =  getCryptocurrenciesFromDb()
        return Observable.concatArrayEager(observableFromApi, observableFromDb)
    }

    fun getCryptocurrenciesFromApi(): Observable<List<Cryptocurrency>> = apiInterface
        .getCryptocurrencies("0")
        .doOnNext {
            Log.e("REPOSITORY API ****", it.size.toString())
            for (item in it) {
                cryptocurrenciesDao.insertCryptocurrency(item)
            }
        }

    fun getCryptocurrenciesFromDb(): Observable<List<Cryptocurrency>> {
        return cryptocurrenciesDao.queryCryptocurrencies()
            .toObservable()
            .doOnNext {
                Log.e("REPOSITORY DB *** ", it.size.toString())
            }

    }

}