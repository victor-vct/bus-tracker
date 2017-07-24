package com.vctapps.bustracker.data.setting.remote

import com.vctapps.bustracker.domain.entity.Settings
import io.reactivex.Maybe

interface RemoteSettingsDatasource{

    fun getDeviceSettings() : Maybe<Settings>

}