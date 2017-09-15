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

    @GET("busmodule/{id_module}/settings/")
    fun getSettings(@Path("id_module") idModule: String): Maybe<Settings>

    @POST("busmodule/{id_module}/position/")
    fun sendLocation(@Path("id_module") idModule: String, @Body busLocation: BusLocation) : Completable

}