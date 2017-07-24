package com.vctapps.bustracker.data.location

import com.vctapps.bustracker.domain.entity.Location
import io.reactivex.Completable

interface LocationRepository{

    fun sendLocation(idBus: Int, location: Location): Completable

}