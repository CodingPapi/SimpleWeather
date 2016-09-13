package com.supermario.enjoy.simpleweather.model

import android.content.Context
import android.util.Log
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.supermario.enjoy.simpleweather.BaseApplication
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.HttpException
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.jackson.JacksonConverterFactory
import rx.Observable
import rx.Scheduler
import rx.android.schedulers.AndroidSchedulers
import rx.lang.kotlin.onError
import rx.schedulers.Schedulers
import java.io.File
import java.util.concurrent.TimeUnit
import retrofit2.Response as rResponse

/**
 * Created by supermario on 2016/7/20.
 */
class RestApi private constructor(context: Context) {

    companion object {
        var api: RestApi? = null
        fun getInstance(context: Context): RestApi {
            if (api == null) {
                api = RestApi(context)
            }
            return api!!
        }
    }

    val weatherApi: WeatherApi

    fun <T> applyTransformer(): Observable.Transformer<T, T> {
        return Observable.Transformer { observable ->
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }
    }

    val logInterceptor: HttpLoggingInterceptor by lazy { HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY) }
    val cache: Cache by lazy { Cache(File(BaseApplication.globalCacheDir, "/net"), 1024 * 1024 * 50) }
    val cacheInterceptor: Interceptor by lazy {
        Interceptor { chain ->
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
    }
    val okClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
                .addInterceptor(logInterceptor)
                .retryOnConnectionFailure(true)
                .connectTimeout(15, TimeUnit.SECONDS)
                .cache(cache).addInterceptor(cacheInterceptor)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .build()
    }
    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
                .baseUrl(WeatherApi.HOST)
                .addConverterFactory(JacksonConverterFactory.create(ObjectMapper().registerModule(KotlinModule())))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okClient)
                .build()
    }

    init {

        weatherApi = retrofit.create(WeatherApi::class.java)
    }

    fun getWeatherData(city: String, key: String): Observable<rResponse<Weather?>> {
//        return weatherApi.getWeatherObservable(city, key)
        return Observable.fromCallable { weatherApi.getWeather(city, key).execute()}
                .compose(applyTransformer<rResponse<Weather?>>())
    }

}