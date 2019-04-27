package com.example.hyperlocal.extensions

import android.support.v4.app.Fragment
import android.util.Log


fun Fragment.getSimpleName() : String {
    return this.javaClass.simpleName
}

fun Fragment.logError(error : Throwable) {
    val name = getSimpleName()
    val length = Math.min(name.length - 1, 20)
    logError(name.substring(0..length), error)
}

fun Fragment.logDebug(message : String?) {
    val name = getSimpleName()
    val length = Math.min(name.length - 1, 20)
    logDebug(name.substring(0..length), message)
}

fun Fragment.replaceFragment(containerID: Int, fragment: Fragment) {
    fragmentManager?.beginTransaction()
        ?.replace(containerID, fragment)
        ?.addToBackStack(getSimpleName())
        ?.commit() ?: Log.e(getSimpleName(), "Null Fragment Manager")
}

fun Fragment.addFragment(containerID: Int, fragment : Fragment) {
    fragmentManager?.beginTransaction()
        ?.add(containerID, fragment)
        ?.commit()
        ?:Log.e(getSimpleName(), "Null Fragment Manager")
}

