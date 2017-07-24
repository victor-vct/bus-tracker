package com.vctapps.bustracker.di

import android.content.Context
import com.vctapps.bustracker.data.location.LocationRepository
import com.vctapps.bustracker.data.setting.SettingsRepository
import com.vctapps.bustracker.domain.SendLocationUseCase
import com.vctapps.bustracker.domain.SendLocationUseCaseImpl
import com.vctapps.bustracker.presentation.MainActivityPresenter
import com.vctapps.bustracker.presentation.MainActivityPresenterImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
@Singleton
class TrackerModule {

    @Provides
    @Singleton
    fun providesSendLocation(context: Context,
                             locationRepository: LocationRepository,
                             settingsRepository: SettingsRepository): SendLocationUseCase{
        return SendLocationUseCaseImpl(context, locationRepository, settingsRepository)
    }

    @Provides
    @Singleton
    fun providesMainActivityPresenter(sendLocationUseCase: SendLocationUseCase): MainActivityPresenter {
        return MainActivityPresenterImpl(sendLocationUseCase)
    }

}