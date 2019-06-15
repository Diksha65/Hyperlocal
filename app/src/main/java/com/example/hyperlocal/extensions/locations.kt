package com.example.hyperlocal.extensions

import android.Manifest
import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.location.Location
import com.google.android.gms.location.LocationServices
import java.io.IOException
import java.lang.Error

fun getCurrentLocation(context: Context) : Location? {

    var location : Location? = null

    try {
        if(isPermissionGranted(context, Manifest.permission.ACCESS_FINE_LOCATION)) {
            LocationServices
                .getFusedLocationProviderClient(context)
                .lastLocation
                .addOnCompleteListener {task ->
                    if(task.isSuccessful) {
                        location = task.result
                    }
                    else{
                        toast(context, "Could not find the location")
                    }
                }
        } else {
            toast(context, "Permission is not granted! Please grant location permission.")
        }
        return location
    } catch (e : SecurityException) {
        logError(Error("getDeviceLocation : SecurityLocation: ${e.message}"))
    }
    return null
}

fun getLocationFromAddress(strAddress: String, context: Context) : Address? {

    var location : Address?
    try {
        val address = Geocoder(context).getFromLocationName(strAddress, 5)
        if (address == null) {
            logError(Error("NULL"))
        }
        location = address[0]
    } catch (e : IOException) {

        location = null
        toast(context, "Some error occured!")
    }

    return location
}