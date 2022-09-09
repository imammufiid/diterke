package com.mufiid.diterke.home

import com.mufiid.utils.listener.FragmentListener

interface HomeFragmentListener : FragmentListener {
    fun onMessageFromActivity(message: String)
}