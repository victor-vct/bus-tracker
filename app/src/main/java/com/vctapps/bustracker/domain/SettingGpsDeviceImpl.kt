package com.vctapps.bustracker.domain

import android.content.Context
import com.google.android.things.contrib.driver.gps.NmeaGpsDriver
import com.vctapps.bustracker.core.BoardDefaults
import com.vctapps.bustracker.core.GpsDefaults
import io.reactivex.Completable


class SettingGpsDeviceImpl(val context: Context): SettingGpsDevice {

    lateinit var gpsDriver: NmeaGpsDriver

    override fun run(): Completable {
        return Completable.create { emmiter ->
            settingGpsDriver()
            emmiter.onComplete()
        }
    }

    private fun settingGpsDriver() {
        gpsDriver = NmeaGpsDriver(context,
                BoardDefaults.uartName,
                GpsDefaults.UART_BAUD,
                GpsDefaults.ACCURACY)

        gpsDriver.register()
    }
}