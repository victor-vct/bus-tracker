package com.vctapps.bustracker.domain

import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import com.vctapps.bustracker.data.location.LocationRepository
import com.vctapps.bustracker.data.setting.SettingsRepository
import com.vctapps.bustracker.domain.entity.BusLocation
import com.vctapps.bustracker.domain.entity.Settings
import io.reactivex.Completable
import io.reactivex.CompletableEmitter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SettingsTrackingImpl(val context: Context,
                           val settingGps: SettingGpsDevice,
                           val locationRepository: LocationRepository,
                           val settingsRepository: SettingsRepository): SettingsTracking {

    lateinit var locationManager: LocationManager

    lateinit var settings: Settings

    lateinit var locationListener: LocationListener

    val TAG = "settingsUseCase"

    override fun run(): Completable {
        return Completable.concatArray (
                settingGps.run(),
                Completable.create { emitter ->

                    settings = settingsRepository.getDeviceSettings()
                            .subscribeOn(Schedulers.io())
                            .blockingGet()

                    settingLocationManager()

                    emitter.onComplete()
                }
        )
    }

    private fun settingLocationManager() {
        locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locationListener = LocationListenerImpl()

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                5000,
                0f,
                locationListener)
       }

    inner class LocationListenerImpl : LocationListener {

        val TAG = "LocationListenerDebug"
        var busLocation: BusLocation? = null

        override fun onLocationChanged(p0: Location?) {
            Log.d(TAG, "BusLocation update: " + p0)
            locationRepository.sendLocation(settings.idModule, getLocationObject(p0))
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
                this.busLocation!!.latitude = locationUnformatted.latitude
                this.busLocation!!.longitude = locationUnformatted.longitude
            }

            return this.busLocation!!
        }
    }

}