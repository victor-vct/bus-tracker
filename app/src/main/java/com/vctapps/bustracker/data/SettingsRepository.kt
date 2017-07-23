package com.vctapps.bustracker.data

import com.vctapps.bustracker.domain.entity.Settings
import io.reactivex.Maybe

interface SettingsRepository {

    fun getDeviceSettings() : Maybe<Settings>

}