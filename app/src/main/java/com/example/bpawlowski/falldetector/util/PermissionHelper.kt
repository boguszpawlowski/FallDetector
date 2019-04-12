package com.example.bpawlowski.falldetector.util

import android.app.Activity
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.karumi.dexter.listener.single.PermissionListener

fun getPermissions(activity: Activity, permissions: List<String>) {
    Dexter.withActivity(activity)
        .withPermissions(permissions)
        .withListener(getListener())
        .check()

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

private fun getListener(): MultiplePermissionsListener = object: MultiplePermissionsListener{
        override fun onPermissionsChecked(report: MultiplePermissionsReport?) {

        }

        override fun onPermissionRationaleShouldBeShown(
            permissions: MutableList<PermissionRequest>?,
            token: PermissionToken?
        ) {

        }
    }
