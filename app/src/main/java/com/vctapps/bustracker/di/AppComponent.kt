package com.vctapps.bustracker.di

import com.vctapps.bustracker.presentation.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class, RepositoryModule::class, TrackerModule::class))
interface AppComponent {

    fun inject(mainActivity: MainActivity)

}