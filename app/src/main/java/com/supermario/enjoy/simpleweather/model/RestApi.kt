package com.supermario.enjoy.simpleweather.model

import android.util.Log
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.jackson.JacksonConverterFactory
import rx.Observable

/**
 * Created by supermario on 2016/7/20.
 */
class RestApi {
    var weatherApi: WeatherApi
    init {
        val retrofit = Retrofit.Builder()
                .baseUrl(WeatherApi.HOST)
                .addConverterFactory(JacksonConverterFactory.create(ObjectMapper().registerModule(KotlinModule())))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()

        weatherApi = retrofit.create(WeatherApi::class.java)
    }

    fun getWeatherData(city: String, key: String): Observable<Weather> {
        return weatherApi.getWeather(city, key)
                .onErrorResumeNext {
                    throwable ->
                    Log.d("jjj", "error:" + throwable)
                    Observable.empty()
                }
    }

    fun getTestData(): Observable<Weather> {
        return getWeatherData("qingdao", WeatherApi.KEY)
    }

}