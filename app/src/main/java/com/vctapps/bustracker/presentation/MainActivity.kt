package com.vctapps.bustracker.presentation

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.vctapps.bustracker.R
import com.vctapps.bustracker.core.TrackerApplication
import com.vctapps.bustracker.core.data.ApiService
import kotlinx.android.synthetic.main.error_message.*
import kotlinx.android.synthetic.main.loading_message.*
import kotlinx.android.synthetic.main.needs_stop_message.*
import kotlinx.android.synthetic.main.waiting_message.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class MainActivity : Activity(), BaseView {

    val TAG = "bus_tracker_log"
    lateinit var error_view: ViewGroup
    lateinit var loading_view: ViewGroup
    lateinit var waiting_view: ViewGroup
    lateinit var needs_stop: ViewGroup
    lateinit var tryAgainButton: Button

    @Inject
    lateinit var presenter: MainActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()

        if (!isPermissionGaranted()) return

    }

    private fun initView() {
        error_view = error_message
        loading_view = loading_message
        waiting_view = waiting_message
        needs_stop = needs_stop_message
        tryAgainButton = tryAgain

        error_view.visibility = View.GONE
        loading_view.visibility = View.GONE
        waiting_view.visibility = View.GONE
        needs_stop.visibility = View.GONE

        tryAgainButton.setOnClickListener({
            presenter.onClickedTryAgainButton()
        })
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
        error_view.visibility = View.GONE
        loading_view.visibility = View.VISIBLE
        waiting_view.visibility = View.GONE
        needs_stop.visibility = View.GONE
    }

    override fun hideLoading() {
        error_view.visibility = View.GONE
        loading_view.visibility = View.GONE
        waiting_view.visibility = View.VISIBLE
        needs_stop.visibility = View.GONE
    }

    override fun showMessageError() {
        error_view.visibility = View.VISIBLE
        loading_view.visibility = View.GONE
        waiting_view.visibility = View.GONE
        needs_stop.visibility = View.GONE
    }

    override fun showAlertToStop() {
        error_view.visibility = View.GONE
        loading_view.visibility = View.GONE
        waiting_view.visibility = View.GONE
        needs_stop.visibility = View.VISIBLE
    }
}
