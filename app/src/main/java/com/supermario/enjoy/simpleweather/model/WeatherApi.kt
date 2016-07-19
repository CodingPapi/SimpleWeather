package com.supermario.enjoy.simpleweather.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by supermario on 2016/7/20.
 */
interface WeatherApi {
    companion object {
        val HOST = "https://api.heweather.com/x3/"
    }

    @GET("weather")
    fun getWeather(@Query("city") city: String, @Query("key") key: String) : Call<WeatherData>

}