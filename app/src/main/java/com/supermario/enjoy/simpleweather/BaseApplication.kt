package com.supermario.enjoy.simpleweather

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.tencent.bugly.crashreport.CrashReport
import kotlin.properties.Delegates

/**
 * Created by lijia8 on 2016/9/9.
 */
class BaseApplication : Application() {
    companion object {
        var globalCacheDir: String? = null
        var networkInfo: NetworkInfo? = null
    }

    override fun onCreate() {
        super.onCreate()
//        CrashReport.initCrashReport(applicationContext, "107c3f69b9", true)
        globalCacheDir = applicationContext.cacheDir.toString()
        val connectivityManager = applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        networkInfo = connectivityManager.activeNetworkInfo
    }
}