package com.example.hyperlocal.extensions

import android.content.Intent
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.crashlytics.android.Crashlytics
import java.lang.Exception

/**
 * Display information
 *//*

fun AppCompatActivity.snackbar(message : String) {  snackbar(this.findViewById(android.R.id.content), message) }

fun AppCompatActivity.toast(message: String) { toast(this, message) }

fun AppCompatActivity.logError(error : Throwable){ logError(tag(), error) }
fun AppCompatActivity.logDebug(message : String?) { logDebug(tag(), message) }

*/
/**
 * Set Name in Action Bar
 *//*

fun AppCompatActivity.setActionBarTitle(name : String) {
    supportActionBar?.title = name
}

*/
/**
 * Switch between activities
 *//*

fun AppCompatActivity.finishAndStart(activity : Class<*>) {
    startActivity(Intent(this, activity))
    finish()
}

fun AppCompatActivity.getSimpleName() : String {
    return this.javaClass.simpleName
}

fun AppCompatActivity.tag() : String {
    val length = this.getSimpleName().length
    val till = Math.min(length - 1, 20)
    return this.getSimpleName().substring(0..till)
}

*/
/**
 * Open a fragment from an activity
 *//*

fun AppCompatActivity.replaceFragment(containerID : Int, fragment : Fragment) {
    supportFragmentManager.beginTransaction()
        .replace(containerID, fragment)
        .commit()
}

fun AppCompatActivity.addFragment(containerID: Int, fragment : Fragment) {
    supportFragmentManager.beginTransaction()
        .add(containerID, fragment)
        .commit()
}*/
