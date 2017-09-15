package com.vctapps.bustracker.data.location

import com.vctapps.bustracker.domain.entity.BusLocation
import io.reactivex.Completable

interface LocationRepository{

    fun sendLocation(idBus: String, busLocation: BusLocation): Completable

}