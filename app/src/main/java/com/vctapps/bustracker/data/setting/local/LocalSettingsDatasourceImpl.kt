package com.vctapps.bustracker.data.setting.local

import android.content.Context
import com.vctapps.bustracker.core.BoardDefaults
import com.vctapps.bustracker.core.InvalidData
import com.vctapps.bustracker.domain.entity.Settings
import io.reactivex.Maybe

class LocalSettingsDatasourceImpl(context: Context) : LocalSettingsDatasource {

    val ID_MODULE = "id_module"
    val ID_BUS = "id_bus"

    var sharedPreferences = context.getSharedPreferences(
            "com.vctapps.SETTINGS",
            Context.MODE_PRIVATE
    )

    init {
        sharedPreferences.edit().clear().commit()

        save(ID_MODULE, BoardDefaults.ID_MODULE_VALUE)
    }

    override fun isDeviceSettings(): Boolean {
        return sharedPreferences.getString(ID_BUS, InvalidData.STRING) != InvalidData.STRING
    }

    override fun getDeviceSettings(): Maybe<Settings> {
        return Maybe.create { emmiter ->
            if(!isDeviceSettings()) {
                emmiter.onComplete()
                return@create
            }

            emmiter.onSuccess(getSettings())
        }
    }

    private fun getSettings(): Settings{
        val idBus = get(ID_BUS)
        val idModule = get(ID_MODULE)

        return Settings(idBus, idModule)
    }

    override fun setSettings(settings: Settings) {
        save(ID_BUS, settings.idBus)
    }

    private fun save(key: String, value: String){
        var editor = sharedPreferences.edit()

        editor.putString(key, value)

        editor.commit()
    }

    private fun get(key: String): String{
        return sharedPreferences.getString(key, InvalidData.STRING)
    }

}