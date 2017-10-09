package com.vctapps.bustracker.presentation

import android.Manifest
import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.animation.*
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
import kotlinx.android.synthetic.main.waiting_to_start_route_message.*
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
    lateinit var waiting_to_start_route_view: ViewGroup
    lateinit var tryAgainButton: Button
    lateinit var arriveAtStopBusButton: Button
    lateinit var startRouteButton: Button

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
        waiting_to_start_route_view = waiting_to_start_route_message
        needs_stop = needs_stop_message
        tryAgainButton = tryAgain
        arriveAtStopBusButton = arriveAtStopBus
        startRouteButton = startRoute

        error_view.visibility = View.GONE
        loading_view.visibility = View.GONE
        waiting_view.visibility = View.GONE
        needs_stop.visibility = View.GONE
        waiting_to_start_route_view.visibility = View.GONE

        tryAgainButton.setOnClickListener({
            presenter.onClickedTryAgainButton()
        })

        arriveAtStopBusButton.setOnClickListener({
            presenter.onClickedSendArrived()
        })

        startRouteButton.setOnClickListener({
            presenter.onClickedStartRouteButton()
        })

        hideBusView()
    }

    private fun hideBusView() {
        var width = getDisplaywidth()

        icBus.translationX = (width * -0.75).toFloat()
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
        waiting_to_start_route_message.visibility = View.GONE
    }

    override fun hideLoading() {
        error_view.visibility = View.GONE
        loading_view.visibility = View.GONE
        waiting_view.visibility = View.VISIBLE
        needs_stop.visibility = View.GONE
        waiting_to_start_route_message.visibility = View.GONE
    }

    override fun showMessageError() {
        error_view.visibility = View.VISIBLE
        loading_view.visibility = View.GONE
        waiting_view.visibility = View.GONE
        needs_stop.visibility = View.GONE
        waiting_to_start_route_message.visibility = View.GONE
    }

    override fun showWaitToStartRoute() {
        error_view.visibility = View.GONE
        loading_view.visibility = View.GONE
        waiting_view.visibility = View.GONE
        needs_stop.visibility = View.GONE
        waiting_to_start_route_message.visibility = View.VISIBLE

        startAnimationWaitingToStartRoute()
    }

    override fun showStartRoute() {
        error_view.visibility = View.GONE
        loading_view.visibility = View.GONE
        waiting_view.visibility = View.GONE
        needs_stop.visibility = View.GONE
        waiting_to_start_route_message.visibility = View.VISIBLE

        startAnimationStartRoute()
    }

    override fun showAlertToStop() {
        error_view.visibility = View.GONE
        loading_view.visibility = View.GONE
        waiting_view.visibility = View.GONE
        needs_stop.visibility = View.VISIBLE

        startAnimationNeedsStop()
    }

    private fun startAnimationWaitingToStartRoute() {
        icBus.animate().interpolator = null

        icBus.animate()
                .translationX(0f)
                .setInterpolator(AccelerateDecelerateInterpolator())
                .setDuration(3000)
                .start()
    }

    private fun startAnimationStartRoute() {
        var width = getDisplaywidth()

        icBus.animate().interpolator = null

        icBus.animate()
                .translationX((width * 0.75).toFloat())
                .setInterpolator(AccelerateInterpolator())
                .setDuration(3000)
                .start()

        messageInformStart.animate()
                .alpha(0f)
                .setDuration(3000)
                .start()

        startRoute.animate()
                .alpha(0f)
                .setDuration(3000)
                .setListener(object : Animator.AnimatorListener {
                    override fun onAnimationRepeat(p0: Animator?) {

                    }

                    override fun onAnimationEnd(p0: Animator?) {
                        hideLoading()
                    }

                    override fun onAnimationCancel(p0: Animator?) {
                    }

                    override fun onAnimationStart(p0: Animator?) {
                    }
                })
    }

    private fun getDisplaywidth(): Int {
        var displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        var width = displayMetrics.widthPixels;
        return width
    }

    private fun startAnimationNeedsStop() {
        var scaleXToMin = ObjectAnimator.ofFloat(icNeedsStop, "scaleX", 0f)
        var scaleYToMin = ObjectAnimator.ofFloat(icNeedsStop, "scaleY", 0f)

        var scaleXToMax = ObjectAnimator.ofFloat(icNeedsStop, "scaleX", 1f)
        var scaleYToMax = ObjectAnimator.ofFloat(icNeedsStop, "scaleY", 1f)

        var animatorSetToMin = AnimatorSet()
        animatorSetToMin.playTogether(scaleXToMin, scaleYToMin)

        var animatorSetToMax = AnimatorSet()
        animatorSetToMax.playTogether(scaleXToMax, scaleYToMax)

        var animator = AnimatorSet()
        animator.duration = 300
        animator.playSequentially(animatorSetToMin, animatorSetToMax)

        animator.removeAllListeners()
        animator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(p0: Animator?) {
                //Do nothing
            }

            override fun onAnimationEnd(p0: Animator?) {
                animator.start()
            }

            override fun onAnimationCancel(p0: Animator?) {
                //Do nothing
            }

            override fun onAnimationStart(p0: Animator?) {
                //Do nothing
            }
        })

        animator.start()
    }
}
