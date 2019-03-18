package com.abhi.crptoadvancedandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.abhi.crptoadvancedandroid.data.Cryptocurrency
import com.abhi.crptoadvancedandroid.data.source.remote.ApiClient
import com.abhi.crptoadvancedandroid.data.source.remote.ApiInterface
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        showCryptocurrencies()
    }

    private fun showCryptocurrencies() {
        val cryptocurrenciesResponse = getCryptocurrencies()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())

        val disposableObserver = cryptocurrenciesResponse.subscribeWith(object : DisposableObserver<List<Cryptocurrency>>() {
            override fun onComplete() {

            }

            override fun onNext(crytpcurrencies: List<Cryptocurrency>) {
                val listSize = crytpcurrencies.size
                Log.e("ITEMS***",listSize.toString())
            }

            override fun onError(e: Throwable) {
                Log.e("ERROR***", e.message)
            }

        })
        compositeDisposable.addAll(disposableObserver)
    }

    private fun getCryptocurrencies(): Observable<List<Cryptocurrency>> {
        val retrofit  = ApiClient.getClient()
        val apiInterface = retrofit.create(ApiInterface::class.java)
        return apiInterface.getCryptocurrencies("0")
    }
}
