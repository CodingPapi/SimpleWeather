package com.supermario.enjoy.simpleweather

import android.app.Application
import android.content.Context
import kotlin.properties.Delegates

/**
 * Created by lijia8 on 2016/9/9.
 */
class BaseApplication : Application() {
    companion object {
        var globalCacheDir: String? = null
    }

    override fun onCreate() {
        super.onCreate()
        globalCacheDir = applicationContext.cacheDir.toString()
    }
}