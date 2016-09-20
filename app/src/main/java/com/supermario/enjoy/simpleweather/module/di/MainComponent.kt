package com.supermario.enjoy.simpleweather.module.di

import com.supermario.enjoy.simpleweather.module.RestApi
import com.supermario.enjoy.simpleweather.module.di.RestModule
import com.supermario.enjoy.simpleweather.module.api.WeatherApi
import dagger.Component

/**
 * Created by lijia8 on 2016/9/20.
 */
@Component(modules = arrayOf(RestModule::class))
interface MainComponent {
    fun inject(api: RestApi)
    fun weatherApi(): WeatherApi

}