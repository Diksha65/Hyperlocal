package com.example.hyperlocal.extensions

import android.Manifest
import android.content.Context
import com.google.android.gms.location.LocationServices
import java.lang.Error

fun getCurrentLocation(context: Context) {
    try {
        if(isPermissionGranted(context, Manifest.permission.ACCESS_FINE_LOCATION)) {
            LocationServices
                .getFusedLocationProviderClient(context)
                .lastLocation
                .addOnCompleteListener {task ->
                    if(task.isSuccessful) {
                        val location = task.result
                        toast(context, "Latitude: ${location!!.latitude}, Longitude: ${location.longitude}")
                    }
                    else
                        toast(context, "Could not find the location")
                }
        } else {
            toast(context, "Permission is not granted! Please grant location permission.")
        }
    } catch (e : SecurityException) {
        logError(Error("getDeviceLocation : SecurityLocation: ${e.message}"))
    }
}