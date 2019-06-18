package com.example.bpawlowski.falldetector.util

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

fun FragmentManager.instantiate(fragmentClass: Class<*>): Fragment =
    fragmentFactory.instantiate(
        ClassLoader.getSystemClassLoader(),
        fragmentClass.name
    )