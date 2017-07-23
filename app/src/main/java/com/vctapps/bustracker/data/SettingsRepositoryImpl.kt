package com.vctapps.bustracker.data

import com.vctapps.bustracker.data.local.LocalSettingsDatasource
import com.vctapps.bustracker.data.remote.RemoteSettingsDatasource
import com.vctapps.bustracker.domain.entity.Settings

class SettingsRepositoryImpl(val localSettingsDatasource: LocalSettingsDatasource,
                             val remoteSettingsDatasource: RemoteSettingsDatasource): SettingsRepository {

    override fun getDeviceSettings(): Settings {
        if(localSettingsDatasource.isDeviceSettings()){
            return localSettingsDatasource.getSettings()
        }

        val settings = remoteSettingsDatasource.getDeviceSettings()

        localSettingsDatasource.setSettings(settings)

        return settings
    }
}