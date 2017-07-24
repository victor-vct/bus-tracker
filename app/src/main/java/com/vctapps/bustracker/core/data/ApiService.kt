package com.vctapps.bustracker.core.data

import com.vctapps.bustracker.domain.entity.Location
import com.vctapps.bustracker.domain.entity.Settings
import io.reactivex.Completable
import io.reactivex.Maybe
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @GET("busmodule/{id_module}/setting/")
    fun getSettings(@Path("id_module") idMdule: Int): Maybe<Settings>

    @POST("bus/{id_bus}/position/")
    fun sendLocation(@Path("id_bus") idBus: Int, @Body location: Location) : Completable

}