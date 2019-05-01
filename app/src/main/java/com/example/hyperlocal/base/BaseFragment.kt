package com.example.hyperlocal.base

import android.R
import android.content.Context
import android.support.v4.app.Fragment
import android.util.Log
import com.example.hyperlocal.extensions.*

abstract class BaseFragment : Fragment() {

    protected val base : BaseActivity by lazy { activity as BaseActivity }

    /**
     * Display information
     */
    fun snackbar(message: String) {
        snackbar(view!!.findViewById(R.id.content), message)
    }

    fun toast(message: String) {
        toast(context!!, message)
    }

    fun logError(error : Throwable) {
        logError(tag(), error)
    }

    fun logDebug(message : String?) {
        logDebug(tag(), message)
    }

    /**
     * Set Name in Action Bar
     */
    fun setActionBarTitle(context : Context, name : String) {

    }

    fun getSimpleName() : String {
        return this.javaClass.simpleName
    }

    fun tag() : String {
        val length = this.getSimpleName().length
        val till = Math.min(length - 1, 20)
        return this.getSimpleName().substring(0..till)
    }

    fun replaceFragment(containerID: Int, fragment: Fragment) {
        fragmentManager?.beginTransaction()
            ?.replace(containerID, fragment)
            ?.addToBackStack(getSimpleName())
            ?.commit() ?: Log.e(getSimpleName(), "Null Fragment Manager")
    }

    fun addFragment(containerID: Int, fragment : Fragment) {
        fragmentManager?.beginTransaction()
            ?.add(containerID, fragment)
            ?.commit()
            ?: Log.e(getSimpleName(), "Null Fragment Manager")
    }

}