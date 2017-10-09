package com.vctapps.bustracker.data;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.vctapps.bustracker.core.BoardDefaults;
import com.vctapps.bustracker.data.setting.local.LocalSettingsDatasource;
import com.vctapps.bustracker.data.setting.local.LocalSettingsDatasourceImpl;
import com.vctapps.bustracker.domain.entity.Settings;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class LocalSettingsDatasourceTest {

    private LocalSettingsDatasource localSettingsDatasource;

    private String ID_MODULE = BoardDefaults.INSTANCE.getID_MODULE_VALUE();
    private String ID_BUS = "456";

    @Before
    public void setUp(){
        Context context = InstrumentationRegistry.getTargetContext();

        localSettingsDatasource = new LocalSettingsDatasourceImpl(context);
    }

    @Test
    public void save_settings_successful(){
        localSettingsDatasource.setSettings(getMockSettings());

        assertEquals(true, localSettingsDatasource.isDeviceSettings());

        Settings settings =localSettingsDatasource.getDeviceSettings()
                .test()
                .assertComplete()
                .values().get(0);

        assertEquals(ID_BUS, settings.getIdBus());
        assertEquals(ID_MODULE, settings.getIdModule());
    }

    private Settings getMockSettings(){
        return new Settings(ID_BUS, ID_MODULE);
    }
}
