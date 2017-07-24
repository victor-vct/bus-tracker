package com.vctapps.bustracker.data.setting.local

import com.vctapps.bustracker.domain.entity.Settings
import io.reactivex.Maybe

interface LocalSettingsDatasource{

    fun isDeviceSettings(): Boolean

    fun getDeviceSettings(): Maybe<Settings>

    fun setSettings(settings: Settings)

}