package com.supermario.enjoy.simpleweather

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Log
import com.tencent.bugly.crashreport.CrashReport
import kotlin.properties.Delegates

/**
 * Created by lijia8 on 2016/9/9.
 */
class BaseApplication : Application() {
    companion object {
        var globalCacheDir: String? = null
        var globalContext: Context? = null
        fun getNetworkInfo(): NetworkInfo? {
            val manager = globalContext?.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
            return manager?.activeNetworkInfo
        }
    }

    override fun onCreate() {
        super.onCreate()
//        CrashReport.initCrashReport(applicationContext, "107c3f69b9", true)
        globalContext = applicationContext
        globalCacheDir = applicationContext.cacheDir.toString()
    }

}