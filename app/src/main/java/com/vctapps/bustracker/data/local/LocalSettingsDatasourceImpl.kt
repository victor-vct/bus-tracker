package com.vctapps.bustracker.data.local

import android.content.Context
import com.vctapps.bustracker.core.BoardDefaults
import com.vctapps.bustracker.core.InvalidData
import com.vctapps.bustracker.domain.entity.Settings

class LocalSettingsDatasourceImpl(context: Context) : LocalSettingsDatasource {

    val ID_MODULE = "id_module"
    val ID_BUS = "id_bus"

    var sharedPreferences = context.getSharedPreferences(
            "com.vctapps.SETTINGS",
            Context.MODE_PRIVATE
    )

    init {
        save(ID_MODULE, BoardDefaults.ID_MODULE_VALUE)
    }

    override fun isDeviceSettings(): Boolean {
        return sharedPreferences.getInt(ID_BUS, InvalidData.INT) != InvalidData.INT
    }

    override fun getSettings(): Settings {
        val idBus = get(ID_BUS)
        val idModule = get(ID_MODULE)

        return Settings(idBus, idModule)
    }

    override fun setSettings(settings: Settings) {
        save(ID_BUS, settings.idBus)
    }

    private fun save(key: String, value: Int){
        var editor = sharedPreferences.edit()

        editor.putInt(key, value)

        editor.commit()
    }

    private fun get(key: String): Int{
        return sharedPreferences.getInt(key, InvalidData.INT)
    }

}