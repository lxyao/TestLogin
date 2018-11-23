@file:JvmName("App")
package com.cfwin.base

import android.app.Application
import android.support.multidex.MultiDex
import com.cfwin.base.utils.CrashHandler

/**
 * 应用程序上下文
 * 加入分包方式<br/>
 * @create at 2018-06-11 by Yao
 */
open class App : Application() {

    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)
        initCrash()
    }

    private fun initCrash() = CrashHandler.getInstance().init(this)
}