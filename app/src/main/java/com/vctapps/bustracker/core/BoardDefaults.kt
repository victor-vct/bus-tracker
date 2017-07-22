package com.vctapps.bustracker.core

import android.os.Build

object BoardDefaults {

    private val DEVICE_EDISON = "edison"
    private val DEVICE_JOULE = "joule"
    private val DEVICE_RPI3 = "rpi3"
    private val DEVICE_IMX6UL_PICO = "imx6ul_pico"
    private val DEVICE_IMX6UL_VVDN = "imx6ul_iopb"
    private val DEVICE_IMX7D_PICO = "imx7d_pico"

    /**
     * Return the UART for current board.
     */
    val uartName: String
        get() {
            when (Build.DEVICE) {
                DEVICE_EDISON -> return "UART1"
                DEVICE_JOULE -> return "UART1"
                DEVICE_RPI3 -> return "UART0"
                DEVICE_IMX6UL_PICO -> return "UART3"
                DEVICE_IMX6UL_VVDN -> return "UART2"
                DEVICE_IMX7D_PICO -> return "UART6"
                else -> throw IllegalStateException("Unknown Build.DEVICE " + Build.DEVICE)
            }
        }

}
