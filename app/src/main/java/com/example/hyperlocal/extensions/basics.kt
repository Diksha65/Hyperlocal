package com.example.hyperlocal.extensions

import android.content.Context
import android.support.design.widget.Snackbar
import android.util.Log
import android.view.View
import android.widget.Toast
import com.crashlytics.android.Crashlytics


/**
 * LOG ERROR
 */
fun logError(error : Throwable) { logError("GlobalLog", error) }
fun logError(tag : String, exception: Throwable) {
    exception.printStackTrace()
    val text = exception.message ?: "NullMessage"
    Log.e(tag, text)
    //Crashlytics.log(Log.ERROR, "F.$tag", text)
}

/**
 * LOG DEBUG
 */
fun logDebug(message : String?) { logDebug("GlobalLog", message) }
fun logDebug(tag : String, message : String?) {
    val text = message ?: "NullMessage"
    Log.d(tag, text)
    //Crashlytics.log(Log.DEBUG, "F:$tag", text)
}

/**
 *  TOAST
 */
fun toast(context : Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

/**
 *  SNACK BAR
 */
fun snackbar(view : View, message : String) {
    Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
}
