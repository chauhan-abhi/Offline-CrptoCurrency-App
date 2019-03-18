@file:Suppress("DEPRECATION")

package com.abhi.crptoadvancedandroid.di

import com.abhi.crptoadvancedandroid.BuildConfig
import com.abhi.crptoadvancedandroid.CryptoApp
import com.abhi.crptoadvancedandroid.di.module.AppModule
import com.abhi.crptoadvancedandroid.di.module.NetModule

object Injector {
    lateinit var appComponent: AppComponent

    fun init(app: CryptoApp) {
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(app))
            .netModule(NetModule(BuildConfig.URL))
            .build()
        appComponent.inject(app)
    }

}