package com.vctapps.bustracker.data.remote

import com.vctapps.bustracker.domain.entity.Settings
import io.reactivex.Maybe
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("busmodule/{id_module}/setting/")
    fun getSettings(@Path("id_module") id_module: Int): Maybe<Settings>

}