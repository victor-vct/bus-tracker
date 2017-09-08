package com.vctapps.bustracker.di

import android.app.Application
import android.content.Context
import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.vctapps.bustracker.core.data.ApiService
import com.vctapps.bustracker.data.location.LocationRepository
import com.vctapps.bustracker.data.location.LocationRepositoryImpl
import com.vctapps.bustracker.data.notification.NeedsStopRepository
import com.vctapps.bustracker.data.notification.NeedsStopRepositoryImpl
import com.vctapps.bustracker.data.setting.SettingsRepository
import com.vctapps.bustracker.data.setting.SettingsRepositoryImpl
import com.vctapps.bustracker.data.setting.local.LocalSettingsDatasource
import com.vctapps.bustracker.data.setting.local.LocalSettingsDatasourceImpl
import com.vctapps.bustracker.data.setting.remote.RemoteSettingsDatasource
import com.vctapps.bustracker.data.setting.remote.RemoteSettingsDatasourceImpl
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@Singleton
class RepositoryModule(val application: Application) {

    val BASE_URL = "http://192.168.25.21:3000"

    @Provides
    @Singleton
    fun providesLocationRepository(apiService: ApiService): LocationRepository{
        return LocationRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun providesSettingsRepository(localSettingsDatasource: LocalSettingsDatasource,
                                   remoteSettingsDatasource: RemoteSettingsDatasource): SettingsRepository{
        return SettingsRepositoryImpl(localSettingsDatasource, remoteSettingsDatasource)
    }

    @Provides
    @Singleton
    fun providesApiService(retrofit: Retrofit): ApiService{
        return retrofit.create<ApiService>(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun providesLocalSettingsDatasource(context: Context): LocalSettingsDatasource{
        return LocalSettingsDatasourceImpl(context)
    }

    @Provides
    @Singleton
    fun providesRemoteSettingsDatasource(apiService: ApiService): RemoteSettingsDatasource{
        return RemoteSettingsDatasourceImpl(apiService)
    }

    @Provides
    @Singleton
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit{
        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(Gson()))
                .client(okHttpClient)
                .build()
    }

    @Provides
    @Singleton
    fun providesHttpLoggingInterceptor(): HttpLoggingInterceptor{
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        return logging
    }

    @Provides
    @Singleton
    fun providesOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient{
        val okhttp = OkHttpClient.Builder()
        okhttp.addInterceptor(loggingInterceptor)

        return okhttp.build()
    }

    @Provides
    @Singleton
    fun providesNeedsStopRepository(): NeedsStopRepository{
        return NeedsStopRepositoryImpl()
    }

}