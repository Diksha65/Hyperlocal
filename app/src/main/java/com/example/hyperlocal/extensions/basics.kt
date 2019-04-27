package com.example.hyperlocal.extensions

import android.util.Log
import com.crashlytics.android.Crashlytics


/**
 * LOG ERROR
 */
fun logError(error : Throwable) { logError("GlobalLog", error) }
fun logError(tag : String, exception: Throwable) {
    exception.printStackTrace()
    val text = exception.message ?: "NullErrorMessage"
    Log.e(tag, text)
    Crashlytics.log(Log.ERROR, "F.$tag", text)
}

/**
 * LOG DEBUG
 */
fun logDebug(message : String?) { logDebug("GlobalLog", message) }
fun logDebug(tag : String, message : String?) {
    val text = message ?: "NullMessage"
    Log.d(tag, text)
    Crashlytics.log(Log.DEBUG, "F:$tag", text)
}