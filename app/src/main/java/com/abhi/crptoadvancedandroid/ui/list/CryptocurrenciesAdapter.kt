package com.abhi.crptoadvancedandroid.ui.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.abhi.crptoadvancedandroid.R
import com.abhi.crptoadvancedandroid.data.Cryptocurrency

class CryptocurrenciesAdapter(
    cryptocurrencies: List<Cryptocurrency>?): RecyclerView.Adapter<CryptocurrenciesAdapter.CryptocurrenciesViewHolder>() {

    private var cryptocurrenciesList = ArrayList<Cryptocurrency>()

    init {
        this.cryptocurrenciesList = cryptocurrencies as ArrayList<Cryptocurrency>
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptocurrenciesViewHolder {
        val itemView = LayoutInflater.from(parent?.context).inflate(R.layout.list_item_concurrency,
            parent, false)
        return CryptocurrenciesViewHolder(itemView)
    }

    override fun getItemCount(): Int = cryptocurrenciesList.size



    override fun onBindViewHolder(holder: CryptocurrenciesViewHolder, position: Int) {
        val cryptocurrencyItem = cryptocurrenciesList[position]
        holder?.bindData(cryptocurrencyItem)
    }

    fun addCryptocurrencies(cryptocurrencies: List<Cryptocurrency>) {
        val initPosition = cryptocurrenciesList.size
        cryptocurrenciesList.addAll(cryptocurrencies)
        /**
         *  this is the way of notifying addition of new data
         *  telling the adapter that a new range of items needs to be inserted in the current list,
         *  starting in one specific position
         *  (the previous list size) and finishing in the new list size.
         */
        notifyItemRangeInserted(initPosition, cryptocurrenciesList.size)
    }

    class CryptocurrenciesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cryptocurrencyId = itemView.findViewById<TextView>(R.id.cryptocurrency_id)

        fun bindData(cryptocurrencyItem: Cryptocurrency) {
            cryptocurrencyId.text = cryptocurrencyItem.id
        }

    }
}