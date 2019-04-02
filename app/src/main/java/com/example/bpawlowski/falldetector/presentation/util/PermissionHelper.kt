package com.example.bpawlowski.falldetector.presentation.util

import android.app.Activity
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener

fun getPermissions(activity: Activity, permissions: List<String>) {
    Dexter.withActivity(activity)
        .withPermissions(permissions)

}//TODO permissions

fun checkPermission(activity: Activity, permission: String, onGranted: () -> Unit) {
    Dexter.withActivity(activity)
        .withPermission(permission)
        .withListener(object : PermissionListener{
            override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                onGranted.invoke()
            }
            override fun onPermissionRationaleShouldBeShown(permission: PermissionRequest?, token: PermissionToken?){
                doNothing
            }
            override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                doNothing
            }
        })
        .check()
}
