package com.abhi.crptoadvancedandroid.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.abhi.crptoadvancedandroid.data.Cryptocurrency
import com.abhi.crptoadvancedandroid.data.source.CryptocurrencyRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class CryptocurrenciesViewModel @Inject constructor(
    val repository: CryptocurrencyRepository
) : ViewModel() {

    val cryptocurrenciesResult: MutableLiveData<List<Cryptocurrency>> = MutableLiveData()
    val cryptocurrenciesError: MutableLiveData<String> = MutableLiveData()
    val cryptocurrenciesLoader: MutableLiveData<Boolean> = MutableLiveData()
    lateinit var disposableObserver: DisposableObserver<List<Cryptocurrency>>

    fun cryptocurrenciesResult(): LiveData<List<Cryptocurrency>> = cryptocurrenciesResult

    fun cryptocurrenciesError(): LiveData<String> = cryptocurrenciesError

    fun cryptocurrenciesLoader(): LiveData<Boolean> = cryptocurrenciesLoader

    fun loadCryptocurrencies(limit: Int, offset: Int) {
        disposableObserver = object : DisposableObserver<List<Cryptocurrency>>() {
            override fun onComplete() {

            }

            override fun onNext(list: List<Cryptocurrency>) {
                cryptocurrenciesResult.postValue(list)
                cryptocurrenciesLoader.postValue(false)
            }

            override fun onError(e: Throwable) {
                cryptocurrenciesError.postValue(e.message)
                cryptocurrenciesLoader.postValue(false)
            }
        }

        /**
         * to avoid weird behaviours when getting the data from the DB and the API,
         * a debounce() operator from RxJava was used in order to give a better user experience:
         * having a connection if the API call is executed fast,
         * maybe you don’t want to display the DB items,
         * and then quickly change them with the new items
         */
        repository.getCryptocurrencies(limit, offset)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .debounce(400, TimeUnit.MILLISECONDS)
            .subscribe(disposableObserver)
    }

    fun disposeElements(){
        if(null != disposableObserver && !disposableObserver.isDisposed) disposableObserver.dispose()
    }
}