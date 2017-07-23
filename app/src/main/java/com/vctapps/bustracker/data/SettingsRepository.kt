package com.vctapps.bustracker.data

import com.vctapps.bustracker.domain.entity.Settings

interface SettingsRepository {

    fun getDeviceSettings() : Settings

}