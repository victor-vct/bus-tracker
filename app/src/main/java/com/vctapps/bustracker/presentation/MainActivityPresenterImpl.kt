package com.vctapps.bustracker.presentation

import com.vctapps.bustracker.domain.SendLocationUseCase
import io.reactivex.disposables.CompositeDisposable

class MainActivityPresenterImpl(val sendLocationUseCase: SendLocationUseCase): MainActivityPresenter {

    lateinit var baseView: BaseView

    val disposable: CompositeDisposable = CompositeDisposable()

    override fun attachTo(baseView: BaseView) {
        this.baseView = baseView

        disposable.add(sendLocationUseCase
                .run()
                .subscribe(
                        {baseView.hideLoading()},
                        {_ -> baseView.showMessageError()}))
    }

    override fun dettachFromView() {
        disposable.clear()
    }
}