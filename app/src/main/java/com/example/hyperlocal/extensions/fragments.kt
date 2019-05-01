/*
package com.example.hyperlocal.extensions

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log

*/
/**
 * Display information
 *//*

fun Fragment.snackbar(message: String) { snackbar(view!!.findViewById(android.R.id.content), message) }

fun Fragment.toast(message: String) { toast(context!!, message) }

fun Fragment.logError(error : Throwable) { logError(tag(), error) }

fun Fragment.logDebug(message : String?) { logDebug(tag(), message) }

*/
/**
 * Set Name in Action Bar
 *//*

fun Fragment.setActionBarTitle(context : Context, name : String) {

}


*/
/**
 * Switch between fragments
 *//*

fun Fragment.getSimpleName() : String {
    return this.javaClass.simpleName
}

fun Fragment.tag() : String {
    val length = this.getSimpleName().length
    val till = Math.min(length - 1, 20)
    return this.getSimpleName().substring(0..till)
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

*/
