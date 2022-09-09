package com.mufiid.diterke.home

import android.os.Bundle
import android.util.Log
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.mufiid.diterke.databinding.FragmentHomeBinding
import com.mufiid.utils.BindingFragment


class HomeFragment: BindingFragment<FragmentHomeBinding>() {
    override fun inflateBinding(): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(layoutInflater)
    }

    override fun onCreateBinding(savedInstanceState: Bundle?) {
        Log.d("asdfasdfasdfasdfasdf1", "asdfasdfasdfasdfasdfasdfasdfads")
    }

}