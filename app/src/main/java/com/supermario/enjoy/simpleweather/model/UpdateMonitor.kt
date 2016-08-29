package com.supermario.enjoy.simpleweather.model

import android.util.Log
import rx.Observable
import rx.Scheduler
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by supermario on 2016/7/17.
 */
class UpdateMonitor {

//    val api: RestApi by lazy { RestApi() }

//    fun getOriginalWeather(city: String): Observable<Weather> {
//        return  api.getWeatherData(city, WeatherApi.KEY)
//    }

    fun testRxjavaThread(): Observable<String> {
        val ob = Observable.create<String> { subscriber -> Thread.sleep(10000)
        subscriber.onNext("sleep")
        subscriber.onCompleted()}
        return ob

    }
}