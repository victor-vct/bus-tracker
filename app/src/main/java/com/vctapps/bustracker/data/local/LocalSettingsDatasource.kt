package com.vctapps.bustracker.data.local

import com.vctapps.bustracker.domain.entity.Settings

interface LocalSettingsDatasource{

    fun isDeviceSettings(): Boolean

    fun getSettings(): Settings

    fun setSettings(settings: Settings)

}