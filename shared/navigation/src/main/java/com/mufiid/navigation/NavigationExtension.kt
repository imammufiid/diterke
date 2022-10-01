package com.mufiid.navigation

import android.content.Context
import android.content.Intent
import kotlin.reflect.KClass

fun Context.intentTo(clazz: KClass<*>) {
    startActivity(Intent(this, clazz.java))
}