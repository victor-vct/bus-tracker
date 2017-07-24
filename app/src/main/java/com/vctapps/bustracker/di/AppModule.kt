package com.vctapps.bustracker.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
@Singleton
class AppModule(val application: Application) {

    @Provides
    @Singleton
    fun providesApplicationContext(): Context = application

}