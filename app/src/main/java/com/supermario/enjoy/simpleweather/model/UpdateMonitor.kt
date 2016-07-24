package com.supermario.enjoy.simpleweather.model

import android.util.Log
import rx.Observable

/**
 * Created by supermario on 2016/7/17.
 */
class UpdateMonitor {

    val api: RestApi
    init {
        api = RestApi()
    }
    fun getWeather(city: String): Observable<String> {
        return api.getTestData().flatMap { data ->
            Log.d("jjj", "data:" + data)
            Observable.just(data.data.first().now.tmp) }

    }
}