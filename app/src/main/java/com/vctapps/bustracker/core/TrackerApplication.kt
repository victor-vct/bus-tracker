package com.vctapps.bustracker.core

import android.support.multidex.MultiDexApplication
import com.vctapps.bustracker.di.*

class TrackerApplication: MultiDexApplication() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = initDagger()
    }

    fun initDagger(): AppComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .repositoryModule(RepositoryModule(this))
            .trackerModule(TrackerModule())
            .build()

}