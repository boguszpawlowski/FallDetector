package com.example.bpawlowski.falldetector.util

import android.app.Application
import android.util.Log
import com.facebook.stetho.Stetho

fun initializeStetho(app: Application){
    Stetho.initializeWithDefaults(app)
    Log.d("StethoHelper","Initializing Stetho")
}