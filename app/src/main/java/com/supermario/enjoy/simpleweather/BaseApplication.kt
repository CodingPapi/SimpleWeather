package com.supermario.enjoy.simpleweather

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.supermario.enjoy.simpleweather.model.DaggerMainComponent
import com.supermario.enjoy.simpleweather.model.MainComponent
import com.supermario.enjoy.simpleweather.model.ModuleStore
import com.tencent.bugly.crashreport.CrashReport

/**
 * Created by lijia8 on 2016/9/9.
 */
class BaseApplication : Application() {
    companion object {
        lateinit var globalCacheDir: String
        lateinit var globalContext: Context
        fun getNetworkInfo(): NetworkInfo? {
            val manager = globalContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            return manager.activeNetworkInfo
        }
        lateinit var mainComponent: MainComponent
    }

    override fun onCreate() {
        super.onCreate()
//        CrashReport.initCrashReport(applicationContext, "107c3f69b9", true)
        globalContext = applicationContext
        globalCacheDir = applicationContext.cacheDir.toString()
        mainComponent = DaggerMainComponent.builder().moduleStore(ModuleStore(globalContext)).build()
    }

}