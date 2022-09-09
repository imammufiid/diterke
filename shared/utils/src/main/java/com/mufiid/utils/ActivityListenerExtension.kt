package com.mufiid.utils

import androidx.fragment.app.Fragment

fun <T: ActivityListener>Fragment.findActivityListener(): T? {
    return activity as? T
}