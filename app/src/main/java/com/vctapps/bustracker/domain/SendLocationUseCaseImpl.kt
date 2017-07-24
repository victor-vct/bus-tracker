package com.vctapps.bustracker.domain

import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import com.google.android.things.contrib.driver.gps.NmeaGpsDriver
import com.vctapps.bustracker.core.BoardDefaults
import com.vctapps.bustracker.core.GpsDefaults
import com.vctapps.bustracker.data.location.LocationRepository
import com.vctapps.bustracker.data.setting.SettingsRepository
import com.vctapps.bustracker.domain.entity.BusLocation
import com.vctapps.bustracker.domain.entity.Settings
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import java.io.IOException

class SendLocationUseCaseImpl(val context: Context,
                              val locationRepository: LocationRepository,
                              val settingsRepository: SettingsRepository): SendLocationUseCase {

    lateinit var locationManager: LocationManager

    lateinit var gpsDriver: NmeaGpsDriver

    lateinit var locationListener: LocationListener

    lateinit var settings: Settings

    val TAG = "sendLocationUseCase"

    override fun run(): Completable {
        return Completable.create {
            configGpsDriver()
            configLocationManager()
            settings = settingsRepository.getDeviceSettings().blockingGet()
            Log.d(TAG, "sendLocationServiceCompleted")
        }
    }

    override fun stop(): Completable {
        return Completable.create {
            gpsDriver.unregister()
            locationManager.removeUpdates(locationListener)

            try {
                gpsDriver.close()
            } catch (e: IOException) {
                Log.w(TAG, "Unable to close GPS driver")
            }
        }
    }

    private fun configLocationManager() {
        locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locationListener = LocationListenerImpl()

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                5000,
                0f,
                locationListener)
    }

    private fun configGpsDriver() {
        gpsDriver = NmeaGpsDriver(context,
                BoardDefaults.uartName,
                GpsDefaults.UART_BAUD,
                GpsDefaults.ACCURACY)

        gpsDriver.register()
    }

    inner class LocationListenerImpl : LocationListener {

        val TAG = "LocationListenerDebug"
        var busLocation: BusLocation? = null

        override fun onLocationChanged(p0: Location?) {
            Log.d(TAG, "BusLocation update: " + p0)
            locationRepository.sendLocation(settings.idBus, getLocationObject(p0))
                    .subscribeOn(Schedulers.io())
                    .doOnError { Log.w(TAG, "error on sending location") }
                    .subscribe()
        }

        override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {
            // Do nothing
        }

        override fun onProviderEnabled(p0: String?) {
            // Do nothing
        }

        override fun onProviderDisabled(p0: String?) {
            // Do nothing
        }

        private fun getLocationObject(locationUnformatted: Location?): BusLocation {
            if(busLocation == null){
                this.busLocation = BusLocation(
                        locationUnformatted!!.speed,
                        locationUnformatted.latitude,
                        locationUnformatted.longitude
                )
            }else{
                this.busLocation!!.speed = locationUnformatted!!.speed
                this.busLocation!!.lat = locationUnformatted.latitude
                this.busLocation!!.lng = locationUnformatted.longitude
            }

            return this.busLocation!!
        }
    }

}