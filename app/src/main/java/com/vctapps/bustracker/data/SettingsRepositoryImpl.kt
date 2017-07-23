package com.vctapps.bustracker.data

import com.vctapps.bustracker.data.local.LocalSettingsDatasource
import com.vctapps.bustracker.data.remote.RemoteSettingsDatasource
import com.vctapps.bustracker.domain.entity.Settings
import io.reactivex.Maybe

class SettingsRepositoryImpl(val localSettingsDatasource: LocalSettingsDatasource,
                             val remoteSettingsDatasource: RemoteSettingsDatasource): SettingsRepository {

    override fun getDeviceSettings(): Maybe<Settings> {
        return Maybe.concat(localSettingsDatasource.getDeviceSettings(),
                remoteSettingsDatasource.getDeviceSettings())
                .firstElement()
                .doOnSuccess { item ->
                    if(!localSettingsDatasource.isDeviceSettings())
                        localSettingsDatasource.setSettings(item)
                }
    }

}