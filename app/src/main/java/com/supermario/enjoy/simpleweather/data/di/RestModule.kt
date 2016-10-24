package com.supermario.enjoy.simpleweather.data.di

import android.content.Context
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.supermario.enjoy.simpleweather.BaseApplication
import com.supermario.enjoy.simpleweather.data.api.WeatherApi
import dagger.Module
import dagger.Provides
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.jackson.JacksonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import kotlin.reflect.jvm.internal.impl.javax.inject.Singleton

/**
 * Created by lijia8 on 2016/9/20.
 */
@Module
class RestModule(val context: Context) {

    @Provides
    fun provideContext(): Context = context

    @Provides
    @Singleton
    fun provideHttpLogInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    @Provides
    @Singleton
    fun provideCache(): Cache = Cache(File(BaseApplication.globalCacheDir, "/net"), 1024 * 1024 * 50)

    @Provides
    @Singleton
    fun provideCacheInterceptor(): Interceptor =  Interceptor { chain ->
        var request: Request = chain.request()
        val connectiveStatus = BaseApplication.getNetworkInfo()?.isAvailable ?: false
        if (!connectiveStatus) {
            request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build()
        }
        val response: Response = chain.proceed(request)
        if (connectiveStatus) {
            val maxAge: Int = 0
            response.newBuilder()
                    .header("Cache-Control", "public, max-age=" + maxAge)
                    .build()

        } else {
            val maxStale: Int = 60 * 60 * 24
            response.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    .build()
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(logInterceptor: HttpLoggingInterceptor, cache: Cache, cacheInterceptor: Interceptor): OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(logInterceptor)
            .retryOnConnectionFailure(true)
            .connectTimeout(15, TimeUnit.SECONDS)
            .cache(cache).addInterceptor(cacheInterceptor)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(okClient: OkHttpClient): Retrofit = Retrofit.Builder()
            .baseUrl(WeatherApi.HOST)
            .addConverterFactory(JacksonConverterFactory.create(ObjectMapper().registerModule(KotlinModule())))
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .client(okClient)
            .build()

    @Provides
    @Singleton
    fun provideWeatherApi(retrofit: Retrofit): WeatherApi = retrofit.create(WeatherApi::class.java)
}