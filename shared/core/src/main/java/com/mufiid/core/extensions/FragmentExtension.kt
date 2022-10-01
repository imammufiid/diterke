package com.mufiid.core.extensions

import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import kotlin.reflect.KClass

fun <T : Fragment> FragmentManager.attachFragment(
    frameLayout: FrameLayout,
    kClass: KClass<T>,
    backStackName: String? = null
): String {
    val instance = kClass.java.newInstance()
    val tagName = kClass.qualifiedName.orEmpty()
    val newBackStackName = backStackName ?: tagName
    this.beginTransaction()
        .add(frameLayout.id, instance, tagName)
        .addToBackStack(newBackStackName)
        .commit()
    return tagName
}

fun <T : Fragment> FragmentManager.replaceFragment(
    frameLayout: FrameLayout,
    kClass: KClass<T>,
    backStackName: String? = null
): String {
    val instance = kClass.java.newInstance()
    val tagName = kClass.qualifiedName.orEmpty()
    val newBackStackName = backStackName ?: tagName
    this.beginTransaction()
        .replace(frameLayout.id, instance, tagName)
        .addToBackStack(newBackStackName)
        .commit()
    return tagName
}