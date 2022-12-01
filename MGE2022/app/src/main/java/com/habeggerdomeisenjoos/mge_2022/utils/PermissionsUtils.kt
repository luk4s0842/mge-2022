package com.habeggerdomeisenjoos.mge_2022.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager

class PermissionsUtils {
    companion object {
        fun hasLocationPermission(context: Context) : Boolean {
            val coarseLocationPermission = context.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
            val fineLocationPermission = context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
            return coarseLocationPermission == PackageManager.PERMISSION_GRANTED ||
                    fineLocationPermission == PackageManager.PERMISSION_GRANTED
        }
    }
}