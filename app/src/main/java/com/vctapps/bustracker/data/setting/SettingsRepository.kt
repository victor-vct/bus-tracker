package com.vctapps.bustracker.data.setting

import com.vctapps.bustracker.domain.entity.Settings
import io.reactivex.Maybe

interface SettingsRepository {

    fun getDeviceSettings() : Maybe<Settings>

}