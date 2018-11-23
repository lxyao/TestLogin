@file:JvmName("LogUtil")
@file:JvmMultifileClass
package com.cfwin.base.utils

import android.util.Log
import com.cfwin.base.BuildConfig

fun e(tag :String = "LogUtil", msg :String?){
    e(tag, msg, false)
}

fun d(tag :String = "LogUtil", msg :String?){
    if(BuildConfig.DEBUG)Log.d(tag, "--------ddd------->{$msg}")
    else PrintWriteUtils.writeErrorLog("{$tag}----------{$msg}")
}

fun i(tag :String = "LogUtil", msg :String?){
    if(BuildConfig.DEBUG)Log.i(tag, "--------iii------->{$msg}")
    else PrintWriteUtils.writeErrorLog("{$tag}----------{$msg}")
}

/**
 * @param onlyLog true 只打印信息，不记录信息
 */
fun e(tag :String = "LogUtil", msg :String?, onlyLog :Boolean = true){
    if(BuildConfig.DEBUG || onlyLog)Log.e(tag, "--------eee------->{$msg}")
    else PrintWriteUtils.writeErrorLog("{$tag}----------{$msg}")
}
