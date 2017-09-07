package com.vctapps.bustracker.domain.entity

import java.io.Serializable

data class BusLocation(var speed: Float,
                       var lat: Double,
                       var lng: Double): Serializable