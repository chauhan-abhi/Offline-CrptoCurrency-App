package com.abhi.crptoadvancedandroid.utils

import android.content.Context
import android.net.ConnectivityManager
import javax.inject.Inject

class Utils @Inject constructor(private val context: Context) {

    fun isConnectedToInternet(): Boolean {
        val connectivity = context.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager

        connectivity?.let {
            it.activeNetworkInfo?.let {
                if (it.isConnected) return true
            }
        }
        return false
    }
}