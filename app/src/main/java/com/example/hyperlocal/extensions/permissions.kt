package com.example.hyperlocal.extensions

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import android.support.v4.app.ActivityCompat.startActivityForResult
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.*
import com.karumi.dexter.listener.single.PermissionListener

fun isPermissionGranted(context : Context, permission : String) : Boolean {
    return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
}

fun requestSinglePermission(context: Context, activity : Activity, permission : String) {
    Dexter.withActivity(activity)
        .withPermission(permission)
        .withListener(object : PermissionListener {
            override fun onPermissionGranted(response : PermissionGrantedResponse) {
                //Single Permission is granted
            }
            override fun onPermissionDenied(response : PermissionDeniedResponse) {
                // check for permanent denial of permission
                if (response.isPermanentlyDenied) {
                    openSettingsDialog(context, activity)
                }
            }
            override fun onPermissionRationaleShouldBeShown(permission : PermissionRequest, token : PermissionToken) {
                token.continuePermissionRequest()
            }
        })
        .withErrorListener { error -> toast(context, "Error occurred! $error") }
        .check()
}

fun openSettingsDialog(context : Context, activity : Activity) {
    val builder = AlertDialog.Builder(context)
    builder.setTitle("Required Permissions")
        .setMessage("This app requires permissons to use all the features. Grant/Revoke them in app settings.")
        .setPositiveButton("Take Me To SETTINGS") { dialog, _ ->
            dialog.cancel()
            openSettings(context, activity)
        }
        .setNegativeButton("Cancel") { dialog, _ ->
            dialog.cancel()
        }
        .show()
}

fun openSettings(context : Context, activity : Activity) {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
    val uri = Uri.fromParts("package", context.packageName, null)
    intent.data = uri
    startActivityForResult(activity, intent, 101, null)
}