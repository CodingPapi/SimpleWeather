package com.supermario.enjoy.simpleweather.model

import dagger.Component

/**
 * Created by lijia8 on 2016/9/20.
 */
@Component(modules = arrayOf(ModuleStore::class))
interface MainComponent {
    fun inject(api: RestApi)
    fun weatherApi(): WeatherApi

}