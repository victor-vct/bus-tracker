package com.vctapps.bustracker.presentation

import android.util.Log
import com.vctapps.bustracker.data.setting.SettingsRepository
import com.vctapps.bustracker.domain.SettingsTracking
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainActivityPresenterImpl(val settingsTracking: SettingsTracking,
                                val settingsRepository: SettingsRepository): MainActivityPresenter {

    lateinit var baseView: BaseView

    val disposable: CompositeDisposable = CompositeDisposable()

    override fun attachTo(baseView: BaseView) {
        this.baseView = baseView

        baseView.showLoading()

        setUp()
    }

    override fun onClickedTryAgainButton() {
        baseView.showLoading()

        setUp()
    }

    private fun setUp(){
        disposable.add(settingsRepository
                .getDeviceSettings()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess {
                    settingsTracking.run().blockingGet()
                }
                .subscribe(
                        {baseView.hideLoading()
                            Log.d("Teste", "configurações realizadas com sucesso")},
                        {error -> baseView.showMessageError()
                            Log.d("Teste", "configurações não realizadas com sucesso. " + error)}))
    }

    override fun dettachFromView() {
        disposable.clear()
    }
}