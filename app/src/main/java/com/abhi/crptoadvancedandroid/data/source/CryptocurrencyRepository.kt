package com.abhi.crptoadvancedandroid.data.source

import android.util.Log
import com.abhi.crptoadvancedandroid.data.Cryptocurrency
import com.abhi.crptoadvancedandroid.data.source.local.CryptocurrenciesDao
import com.abhi.crptoadvancedandroid.data.source.remote.ApiInterface
import com.abhi.crptoadvancedandroid.utils.Utils
import io.reactivex.Observable
import javax.inject.Inject

class CryptocurrencyRepository @Inject constructor(
    val apiInterface: ApiInterface,
    val cryptocurrenciesDao: CryptocurrenciesDao,
    val utils: Utils
) {
    fun getCryptocurrencies(limit: Int, offset: Int): Observable<List<Cryptocurrency>> {
        val hasConnection = utils.isConnectedToInternet()
        var observableFromApi: Observable<List<Cryptocurrency>>? = null
        if (hasConnection) {
            observableFromApi = getCryptocurrenciesFromApi()
        }
        val observableFromDb = getCryptocurrenciesFromDb(limit, offset)
        return if (hasConnection) Observable.concatArrayEager(observableFromApi, observableFromDb)
        else
            observableFromDb
    }

    fun getCryptocurrenciesFromApi(): Observable<List<Cryptocurrency>> = apiInterface
        .getCryptocurrencies("0")
        .doOnNext {
            Log.e("REPOSITORY API ****", it.size.toString())
            for (item in it) {
                cryptocurrenciesDao.insertCryptocurrency(item)
            }
        }

    fun getCryptocurrenciesFromDb(limit: Int, offset: Int): Observable<List<Cryptocurrency>> {
        return cryptocurrenciesDao.queryCryptocurrencies(limit, offset)
            .toObservable()
            .doOnNext {
                Log.e("REPOSITORY DB *** ", it.size.toString())
            }

    }

}