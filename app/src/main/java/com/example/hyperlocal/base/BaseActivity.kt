package com.example.hyperlocal.base

import android.R
import android.content.Intent
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.example.hyperlocal.PermissionsModule
import com.example.hyperlocal.extensions.*

abstract class BaseActivity : AppCompatActivity() {

    /**
     * Handling permissions
     */
    private val permissionModule = PermissionsModule()

    /*fun withPermissions(vararg permissions : String) {
        permissionModule.PermissionBuilder()
    }*/

    /**
     * Display information
     */
    fun snackbar(message : String) {
        snackbar(this.findViewById(R.id.content), message)
    }
    fun toast(message: String) {
        toast(this, message)
    }
    fun logError(error : Throwable){
        logError(tag(), error)
    }
    fun logDebug(message : String?) {
        logDebug(tag(), message)
    }

    /**
     * Set Name in Action Bar
     */
    fun setActionBarTitle(name : String) {
        supportActionBar?.title = name
    }

    /**
     * Switch between activities
     */
    fun finishAndStart(activity : Class<*>) {
        startActivity(Intent(this, activity))
        finish()
    }
    fun getSimpleName() : String {
        return this.javaClass.simpleName
    }
    fun tag() : String {
        val length = this.getSimpleName().length
        val till = Math.min(length - 1, 20)
        return this.getSimpleName().substring(0..till)
    }

    /**
     * Open a fragment from an activity
     */
    fun replaceFragment(containerID : Int, fragment : Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(containerID, fragment)
            .commit()
    }
    fun addFragment(containerID: Int, fragment : Fragment) {
        supportFragmentManager.beginTransaction()
            .add(containerID, fragment)
            .commit()
    }
    
}