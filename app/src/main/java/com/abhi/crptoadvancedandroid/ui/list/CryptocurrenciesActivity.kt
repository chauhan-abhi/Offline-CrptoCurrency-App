package com.abhi.crptoadvancedandroid.ui.list

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.abhi.crptoadvancedandroid.R
import com.abhi.crptoadvancedandroid.data.Cryptocurrency
import com.abhi.crptoadvancedandroid.utils.Constants
import com.abhi.crptoadvancedandroid.utils.InfiniteScrollListener
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class CryptocurrenciesActivity : AppCompatActivity() {

    @Inject
    lateinit var cryptocurrenciesViewModelFactory: CryptocurrenciesViewModelFactory
    lateinit var cryptocurrenciesViewModel: CryptocurrenciesViewModel
    var cryptocurrenciesAdapter = CryptocurrenciesAdapter(ArrayList())
    var currentPage = 0
    //val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AndroidInjection.inject(this)
        initializeRecycler()
        cryptocurrenciesViewModel = ViewModelProviders.of(this, cryptocurrenciesViewModelFactory).get(
            CryptocurrenciesViewModel::class.java
        )

        progressBar.visibility = View.VISIBLE
        loadData()

        cryptocurrenciesViewModel.cryptocurrenciesResult().observe(this,
            Observer<List<Cryptocurrency>> {
                if (it != null) {
                    val position = cryptocurrenciesAdapter.itemCount
                    cryptocurrenciesAdapter.addCryptocurrencies(it)
                    recycler.adapter = cryptocurrenciesAdapter
                    recycler.scrollToPosition(position - Constants.LIST_SCROLLING)
                }
            })

        cryptocurrenciesViewModel.cryptocurrenciesError().observe(this,
            Observer<String> {
                if (it != null) {
                    Toast.makeText(this, resources.getString(R.string.cryptocurrency_error_message) + it,
                        Toast.LENGTH_SHORT).show()
                }
            })

        cryptocurrenciesViewModel.cryptocurrenciesLoader().observe(this,
            Observer<Boolean> {
                if (it == false) progressBar.visibility = View.GONE
            })
        //showCryptocurrencies()
    }

    private fun loadData() {
        cryptocurrenciesViewModel.loadCryptocurrencies(Constants.LIMIT, currentPage * Constants.OFFSET)
        currentPage++
    }

    private fun initializeRecycler() {
        val gridLayoutManager = GridLayoutManager(this, 1)
        gridLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recycler.apply {
            setHasFixedSize(true)
            layoutManager = gridLayoutManager
            addOnScrollListener(InfiniteScrollListener({ loadData() }, gridLayoutManager))
        }
    }

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
