package com.supermario.enjoy.simpleweather.model

import android.content.Context
import android.util.Log
import com.supermario.enjoy.simpleweather.BaseApplication
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import javax.inject.Inject
import retrofit2.Response as rResponse

/**
 * Created by supermario on 2016/7/20.
 */
object RestApi {

    fun <T> applyTransformer(): Observable.Transformer<T, T> {
        return Observable.Transformer { observable ->
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }
    }

   lateinit var weatherApi: WeatherApi

    init {
        weatherApi = BaseApplication.mainComponent.weatherApi()
    }

    fun getWeatherData(city: String, key: String): Observable<rResponse<Weather?>> {
//        return weatherApi.getWeatherObservable(city, key)
        return Observable.fromCallable { weatherApi.getWeather(city, key).execute() }
                .compose(applyTransformer<rResponse<Weather?>>())
                .onErrorResumeNext { e ->
                    Log.d("jjj", "error:" + e)
                    Observable.create { }
                }
    }

}