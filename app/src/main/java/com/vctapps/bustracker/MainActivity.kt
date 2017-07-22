package com.vctapps.bustracker

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.google.android.things.contrib.driver.gps.NmeaGpsDriver
import com.vctapps.bustracker.core.BoardDefaults

class MainActivity : Activity() {

    val TAG = "bus_tracker_log"

    val UART_BAUD = 9600
    val ACCURACY = 2.5f

    lateinit var locationManager: LocationManager
    lateinit var gpsDriver: NmeaGpsDriver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!isPermissionGaranted()) return

        configGpsDriver()

        configLocationManager()
    }

    private fun isPermissionGaranted(): Boolean {
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "No permission")
            return false
        }
        return true
    }

    private fun configLocationManager() {
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, LocationListenerImpl)
    }

    private fun configGpsDriver() {
        gpsDriver = NmeaGpsDriver(this, BoardDefaults.uartName, UART_BAUD, ACCURACY)

        gpsDriver.register()
    }

    object LocationListenerImpl : LocationListener{

        val TAG = "LocationListenerDebug"

        override fun onLocationChanged(p0: Location?) {
            Log.d(TAG, "Location update: " + p0)
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
    }
}
