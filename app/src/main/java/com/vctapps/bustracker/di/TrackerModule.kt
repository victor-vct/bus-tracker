package com.vctapps.bustracker.di

import android.content.Context
import com.vctapps.bustracker.data.location.LocationRepository
import com.vctapps.bustracker.data.notification.NeedsStopRepository
import com.vctapps.bustracker.data.setting.SettingsRepository
import com.vctapps.bustracker.data.setting.remote.RemoteSettingsDatasource
import com.vctapps.bustracker.domain.SettingGpsDevice
import com.vctapps.bustracker.domain.SettingGpsDeviceImpl
import com.vctapps.bustracker.domain.SettingsTracking
import com.vctapps.bustracker.domain.SettingsTrackingImpl
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
    fun providesSettingsTracking(context: Context,
                                 locationRepository: LocationRepository,
                                 settingsRepository: SettingsRepository,
                                 settingGpsDevice: SettingGpsDevice): SettingsTracking{
        return SettingsTrackingImpl(context, settingGpsDevice, locationRepository, settingsRepository)
    }

    @Provides
    @Singleton
    fun providesMainActivityPresenter(settingsTracking: SettingsTracking,
                                      settingsRepository: SettingsRepository,
                                      needsStopRepository: NeedsStopRepository): MainActivityPresenter {
        return MainActivityPresenterImpl(settingsTracking, settingsRepository, needsStopRepository)
    }

    @Provides
    @Singleton
    fun providesSettingsGpsDevice(context: Context): SettingGpsDevice{
        return SettingGpsDeviceImpl(context)
    }

}