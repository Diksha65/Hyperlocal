package com.example.hyperlocal

import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import com.example.hyperlocal.base.BaseActivity
import com.example.hyperlocal.extensions.logDebug
import com.example.hyperlocal.extensions.logError
import java.util.concurrent.ConcurrentHashMap

class PermissionsModule {

    //Stores the permissions along with their requestPermission status
    private class PermissionAction {
        var permissions = arrayListOf<String>()

        var requestPermissions: () -> Unit = { logError(Error("Permission Request not made")) }

        var onGranted: () -> Unit = { logDebug("onGranted") }

        var onError: ((Error) -> Unit) = { error -> logError("DefaultARACallback", error) }
    }

    //stores the key value pair of request code and permission action object of above class
    private val permissionRequests: MutableMap<Int, PermissionAction> = ConcurrentHashMap()

    inner class PermissionBuilder(private val requestCode: Int) {

        fun withPermissions(activity: BaseActivity, permissions: ArrayList<String>): PermissionBuilder {

            permissionRequests[requestCode] = PermissionAction().apply {
                this.permissions = permissions
                requestPermissions =
                    { ActivityCompat.requestPermissions(activity, permissions.toTypedArray(), requestCode) }
            }
            return this
        }

        fun execute(): PermissionBuilder {
            permissionRequests[requestCode].apply {
                this?.onGranted = { logDebug("DefaultPermitCallback", "Default") }
                this?.onError = { error -> logError("DefaultPermitCallback", error) }
                this?.requestPermissions?.invoke()
            }
            return this
        }
    }

    //checks if the particular permission has been granted or not
    private fun checkPermission(activity: BaseActivity, permission: String): Boolean {
        return ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED
    }

    //creates a map of permissions and results
    private fun createMap(permissions: Array<out String>, grantResult: IntArray): MutableMap<String, Boolean> {
        val map: MutableMap<String, Boolean> = HashMap()
        permissions.mapIndexed { index, permission ->
            map[permission] = grantResult[index] == PackageManager.PERMISSION_GRANTED
        }
        return map
    }


    fun onRequestPermissionResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {

        val permissionResult = createMap(permissions, grantResults)

        for ((RC, action) in permissionRequests) {
            if (RC == requestCode) {
                val allGranted = action.permissions.map { permission -> permissionResult[permission] ?: false }
                    .reduce { acc, b -> acc && b }
                if (allGranted) {
                    action.onGranted()
                } else {
                    action.onError(Error("All permissions are not granted"))
                    action.permissions
                        .forEach { permission ->
                            if (!(permissionResult[permission] ?: false))
                                action.onError(Error("$permission not granted"))
                        }
                }
                permissionRequests.remove(RC)
            }
        }
    }
}