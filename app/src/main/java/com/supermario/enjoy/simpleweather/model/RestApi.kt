package com.supermario.enjoy.simpleweather.model

import android.content.Context
import android.util.Log
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.jackson.JacksonConverterFactory
import rx.Observable
import rx.Scheduler
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * Created by supermario on 2016/7/20.
 */
class RestApi private constructor(context: Context) {

    companion object  {
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

    init {
        val logInterceptor = HttpLoggingInterceptor()
        logInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val okClient = OkHttpClient.Builder()
                .addInterceptor(logInterceptor)
                .retryOnConnectionFailure(true)
                .connectTimeout(15, TimeUnit.SECONDS)
                .build()
        val retrofit = Retrofit.Builder()
                .baseUrl(WeatherApi.HOST)
                .addConverterFactory(JacksonConverterFactory.create(ObjectMapper().registerModule(KotlinModule())))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okClient)
                .build()

        weatherApi = retrofit.create(WeatherApi::class.java)
        Log.d("jjj","RestApi init weatherApi:" + weatherApi)
    }

    fun getWeatherData(city: String, key: String): Observable<Weather> {
//        return weatherApi.getWeatherObservable(city, key)
        return Observable.fromCallable { weatherApi.getWeather(city, key).execute().body() }
                .compose(applyTransformer<Weather>())
    }

    fun getTestData(): Observable<Weather> {
        return getWeatherData("qingdao", WeatherApi.KEY)
    }

}