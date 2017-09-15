package com.vctapps.bustracker.domain.entity

import com.google.gson.annotations.SerializedName

data class Settings(@SerializedName("IdBus") var idBus: String,
                    @SerializedName("IdBusModule") var idModule: String)