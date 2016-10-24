package com.supermario.enjoy.simpleweather.data.di

import com.supermario.enjoy.simpleweather.data.RestApi
import com.supermario.enjoy.simpleweather.data.di.RestModule
import com.supermario.enjoy.simpleweather.data.api.WeatherApi
import dagger.Component

/**
 * Created by lijia8 on 2016/9/20.
 */
@Component(modules = arrayOf(RestModule::class))
interface MainComponent {
    fun inject(api: RestApi)
    fun weatherApi(): WeatherApi

}