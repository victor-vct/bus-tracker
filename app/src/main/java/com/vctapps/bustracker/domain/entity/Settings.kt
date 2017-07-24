package com.vctapps.bustracker.domain.entity

import com.google.gson.annotations.SerializedName

data class Settings(@SerializedName("id_bus") var idBus: Int,
                    @SerializedName("id_module") var idModule: Int)