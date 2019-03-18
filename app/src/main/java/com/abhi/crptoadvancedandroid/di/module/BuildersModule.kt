package com.abhi.crptoadvancedandroid.di.module

import com.abhi.crptoadvancedandroid.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BuildersModule {

@ContributesAndroidInjector
abstract fun contributeMainActivity(): MainActivity
}