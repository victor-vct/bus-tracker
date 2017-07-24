package com.vctapps.bustracker.data.location

import com.vctapps.bustracker.core.data.ApiService
import com.vctapps.bustracker.domain.entity.BusLocation
import io.reactivex.Completable

class LocationRepositoryImpl(val apiService: ApiService): LocationRepository {

    override fun sendLocation(idBus: Int, busLocation: BusLocation): Completable {
        return apiService.sendLocation(idBus, busLocation)
    }

}