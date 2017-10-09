package com.vctapps.bustracker.presentation

import android.util.Log
import com.vctapps.bustracker.data.notification.NeedsStopRepository
import com.vctapps.bustracker.data.setting.SettingsRepository
import com.vctapps.bustracker.domain.SettingsTracking
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainActivityPresenterImpl(private val settingsTracking: SettingsTracking,
                                private val settingsRepository: SettingsRepository,
                                private val needsStopRepository: NeedsStopRepository): MainActivityPresenter {

    lateinit var baseView: BaseView

    private val disposable: CompositeDisposable = CompositeDisposable()

    override fun attachTo(baseView: BaseView) {
        this.baseView = baseView

        baseView.showLoading()

        setUp()
    }

    override fun onClickedTryAgainButton() {
        baseView.showLoading()

        setUp()
    }

    override fun onClickedStartRouteButton() {
        baseView.showStartRoute()

        disposable.add(settingsTracking.run()
                .subscribe())
    }

    override fun onClickedFinishRouteButton() {
        disposable.clear()

        baseView.showWaitToStartRoute()
    }

    override fun onClickedSendArrived() {
        disposable.add(
                needsStopRepository.sendAlertThatArrived()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            baseView.hideLoading()
                        })
        )
    }

    private fun setUp(){
        disposable.add(settingsRepository
                .getDeviceSettings()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {baseView.showWaitToStartRoute()
                            Log.d("BusTracker", "configurações realizadas com sucesso")},
                        {error -> baseView.showMessageError()
                            Log.d("BusTracker", "configurações não realizadas com sucesso. " + error)}))

        disposable.add(needsStopRepository
                .register()
                .subscribe({
                    baseView.showAlertToStop()
                }))

    }

    override fun dettachFromView() {
        disposable.clear()
    }
}