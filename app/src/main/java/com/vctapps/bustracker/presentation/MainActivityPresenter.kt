package com.vctapps.bustracker.presentation

interface MainActivityPresenter {

    fun attachTo(baseView: BaseView)

    fun dettachFromView()

}