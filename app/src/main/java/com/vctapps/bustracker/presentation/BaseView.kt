package com.vctapps.bustracker.presentation

interface BaseView{

    fun showLoading()

    fun hideLoading()

    fun showMessageError()

    fun showAlertToStop()

    fun showWaitToStartRoute()

    fun showStartRoute()

}