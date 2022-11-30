package com.habeggerdomeisenjoos.mge_2022.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager

class PermissionsUtils {
    companion object {
        fun hasLocationPermission(context: Context) : Boolean {
            val status = context.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
            return status == PackageManager.PERMISSION_GRANTED
        }
    }
}