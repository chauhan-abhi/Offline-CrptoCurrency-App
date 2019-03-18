package com.abhi.crptoadvancedandroid.ui.list

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.GridLayout
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.abhi.crptoadvancedandroid.R
import com.abhi.crptoadvancedandroid.data.Cryptocurrency
import com.abhi.crptoadvancedandroid.data.source.remote.ApiClient
import com.abhi.crptoadvancedandroid.data.source.remote.ApiInterface
import com.abhi.crptoadvancedandroid.utils.Constants
import dagger.android.AndroidInjection
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class CryptocurrenciesActivity : AppCompatActivity() {

    @Inject
    lateinit var cryptocurrenciesViewModelFactory: CryptocurrenciesViewModelFactory
    lateinit var cryptocurrenciesViewModel: CryptocurrenciesViewModel
    //var cryptocurrenciesAdapter = CryptocurrenciesAdapter(ArrayList())
    //var currentPage = 0
    //val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AndroidInjection.inject(this)
       //initializeRecycler()
        cryptocurrenciesViewModel = ViewModelProviders.of(this, cryptocurrenciesViewModelFactory).get(
            CryptocurrenciesViewModel::class.java)

        //progressBar.visibility = View.VISIBLE
        loadData()

        cryptocurrenciesViewModel.cryptocurrenciesResult().observe(this,
            Observer<List<Cryptocurrency>> {
                hello_world_textview.text = "Hello ${it?.size} cryptocurrencies"
            })

        cryptocurrenciesViewModel.cryptocurrenciesError().observe(this,
            Observer<String> {
                hello_world_textview.text = "Hello error $it"
            })

        /*cryptocurrenciesViewModel.cryptocurrenciesLoader().observe(this,
            Observer<Boolean> {
                if (it == false) progressBar.visibility = View.GONE
            })*/
        //showCryptocurrencies()
    }

    private fun loadData() {
        cryptocurrenciesViewModel.loadCryptocurrencies()
        //currentPage++
    }

   /* private fun initializeRecycler() {
        val gridLayoutManager = GridLayoutManager(this,1)
        gridLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recycler.apply {
            setHasFixedSize(true)
            layoutManager = gridLayoutManager
            addOnScrollListener()
        }
    }*/

    /*private fun showCryptocurrencies() {
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
    }*/

    /*private fun getCryptocurrencies(): Observable<List<Cryptocurrency>> {
        val retrofit  = ApiClient.getClient()
        val apiInterface = retrofit.create(ApiInterface::class.java)
        return apiInterface.getCryptocurrencies("0")
    }*/

    override fun onDestroy() {
        cryptocurrenciesViewModel.disposeElements()
        super.onDestroy()
    }
}
