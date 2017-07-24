package com.vctapps.bustracker.presentation

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.vctapps.bustracker.core.TrackerApplication
import com.vctapps.bustracker.core.data.ApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class MainActivity : Activity(), BaseView {

    val TAG = "bus_tracker_log"

    @Inject
    lateinit var presenter: MainActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!isPermissionGaranted()) return

    }

    override fun onResume() {
        super.onResume()

        injectDependencies()

        presenter.attachTo(this)
    }

    fun injectDependencies(){
        (application as TrackerApplication).appComponent.inject(this)
    }

    private fun isPermissionGaranted(): Boolean {
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                checkSelfPermission(Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "No permission")
            return false
        }
        return true
    }

    override fun showLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showMessageError() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showAlertToStop() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
