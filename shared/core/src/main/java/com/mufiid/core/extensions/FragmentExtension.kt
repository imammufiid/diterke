package com.mufiid.core.extensions

import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import kotlin.reflect.KClass

fun <T : Fragment> FragmentActivity.attachFragment(frameLayout: FrameLayout, kClass: KClass<T>): String {
    val instance = kClass.java.newInstance()
    val tagName = kClass.qualifiedName.orEmpty()
    supportFragmentManager
        .beginTransaction()
        .add(frameLayout.id, instance, tagName)
        .commit()
    return tagName
}