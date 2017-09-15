package com.vctapps.bustracker.core

import android.os.Build

object BoardDefaults {

    val ID_MODULE_VALUE = "59bbef177cc87e0012a744e7"

    private val DEVICE_EDISON = "edison"
    private val DEVICE_RPI3 = "rpi3"
    val uartName: String
        get() {
            when (Build.DEVICE) {
                DEVICE_EDISON -> return "UART1"
                DEVICE_RPI3 -> return "UART0"
                else -> throw IllegalStateException("Unknown Build.DEVICE " + Build.DEVICE)
            }
        }

}
