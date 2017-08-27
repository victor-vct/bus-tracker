package com.vctapps.bustracker.presentation

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

        settingsRepository.getDeviceSettings()
                .subscribe({
//                    disposable.add(settingsTracking
//                            .run()
//                            .subscribeOn(Schedulers.io())
//                            .observeOn(AndroidSchedulers.mainThread())
//                            .subscribe(
//                                    {baseView.hideLoading()},
//                                    {_ -> baseView.showMessageError()}))
                },{error -> baseView.showMessageError()})
    }

    override fun dettachFromView() {
        disposable.clear()
    }
}