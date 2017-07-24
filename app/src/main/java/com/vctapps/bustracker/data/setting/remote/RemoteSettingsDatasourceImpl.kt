package com.vctapps.bustracker.data.setting.remote

import com.vctapps.bustracker.core.BoardDefaults
import com.vctapps.bustracker.core.data.ApiService
import com.vctapps.bustracker.domain.entity.Settings
import io.reactivex.Maybe

class RemoteSettingsDatasourceImpl(val apiService: ApiService): RemoteSettingsDatasource {

    override fun getDeviceSettings(): Maybe<Settings> {
        return apiService.getSettings(BoardDefaults.ID_MODULE_VALUE)
    }
}