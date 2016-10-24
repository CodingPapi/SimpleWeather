package com.supermario.enjoy.simpleweather.data.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable

/**
 * Created by supermario on 2016/7/20.
 */
interface WeatherApi {
    companion object {
        val HOST = "https://api.heweather.com/x3/"
        val KEY = "41054a8f1d1a4ac992b1683e47a50146"
    }

    @GET("weather")
    fun getWeather(@Query("city") city: String, @Query("key") key: String) : Call<Weather?>

    @GET("weather")
    fun getWeatherObservable(@Query("city") city: String, @Query("key") key: String) : Observable<Weather?>

}