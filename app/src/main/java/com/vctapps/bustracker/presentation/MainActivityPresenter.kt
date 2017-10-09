package com.vctapps.bustracker.presentation

interface MainActivityPresenter {

    fun attachTo(baseView: BaseView)

    fun dettachFromView()

    fun onClickedTryAgainButton()

    fun onClickedStartRouteButton()

    fun onClickedFinishRouteButton()

    fun onClickedSendArrived()

}