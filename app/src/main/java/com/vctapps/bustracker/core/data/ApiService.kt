package com.vctapps.bustracker.core.data

import com.vctapps.bustracker.BuildConfig
import com.vctapps.bustracker.domain.entity.BusLocation
import com.vctapps.bustracker.domain.entity.Settings
import io.reactivex.Completable
import io.reactivex.Maybe
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    companion object {
        val BASE_URL = BuildConfig.BASE_URL
    }

    @GET("busmodule/{id_module}/setting/")
    fun getSettings(@Path("id_module") idModule: Int): Maybe<Settings>

    @POST("bus/{id_bus}/position/")
    fun sendLocation(@Path("id_bus") idBus: Int, @Body busLocation: BusLocation) : Completable

}