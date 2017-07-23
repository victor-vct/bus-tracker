package com.vctapps.bustracker.data.remote

import com.vctapps.bustracker.domain.entity.Settings

interface RemoteSettingsDatasource{

    fun getDeviceSettings() : Settings

}