package com.abhi.crptoadvancedandroid.di

import com.abhi.crptoadvancedandroid.CryptoApp

object Injector {
    lateinit var appComponent: AppComponent

    fun init(app: CryptoApp) {
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(app))
            .build()
        appComponent.inject(app)
    }

}