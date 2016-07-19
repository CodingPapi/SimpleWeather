package com.supermario.enjoy.simpleweather.model

import com.supermario.enjoy.simpleweather.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.Interceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.jackson.JacksonConverterFactory

/**
 * Created by supermario on 2016/7/20.
 */
class RestApi {
    var weatherApi: WeatherApi
    init {
        val retrofit = Retrofit.Builder()
                .baseUrl(WeatherApi.HOST)
                .addConverterFactory(JacksonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()

        weatherApi = retrofit.create(WeatherApi::class.java)
    }

    fun getWeatherData(city: String, key: String): Call<WeatherData> {
        return weatherApi.getWeather(city, key)
    }
}