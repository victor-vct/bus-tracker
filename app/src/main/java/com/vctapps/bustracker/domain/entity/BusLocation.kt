package com.vctapps.bustracker.domain.entity

import java.io.Serializable

data class BusLocation(var speed: Float,
                       var latitude: Double,
                       var longitude: Double): Serializable