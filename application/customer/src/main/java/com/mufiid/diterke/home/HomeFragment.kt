package com.mufiid.diterke.home

import android.Manifest
import android.location.Location
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.mufiid.core.extensions.toLatLng
import com.mufiid.core.state.StateEventSubscriber
import com.mufiid.diterke.R
import com.mufiid.diterke.databinding.FragmentHomeBinding
import com.mufiid.utils.BindingFragment
import com.mufiid.utils.findActivityListener
import org.koin.androidx.viewmodel.ext.android.viewModel
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions


class HomeFragment : BindingFragment<FragmentHomeBinding>(), HomeFragmentListener {

    companion object {
        private const val RC_LOCATION = 16
    }

    private val viewModel by viewModel<HomeViewModel>()
    private lateinit var map: GoogleMap

    override fun inflateBinding(): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(layoutInflater)
    }

    override fun onCreateBinding(savedInstanceState: Bundle?) {
        val mapFragment = childFragmentManager
            .findFragmentById(R.id.map_view) as SupportMapFragment
        mapFragment.getMapAsync {
            map = it
            getLocationWithPermission()
        }
        viewModel.subscribe(subscriberLocation)
    }

    private val subscriberLocation = object : StateEventSubscriber<Location> {
        override fun onIdle() {
            println("--- EVENT-LOCATION => OnIdle")
        }

        override fun onLoading() {
            println("--- EVENT-LOCATION => OnLoading")
            binding.progressBar.isVisible = true
        }

        override fun onFailure(throwable: Throwable) {
            println("--- EVENT-LOCATION => OnFailure: ${throwable.message}")
            binding.progressBar.isVisible = false
        }

        override fun onSuccess(data: Location) {
            println("--- EVENT-LOCATION => onSuccess: $data")
            binding.progressBar.isVisible = false
            val cameraUpdate = CameraUpdateFactory.newLatLngZoom(data.toLatLng(), 14f)
            map.animateCamera(cameraUpdate)
            findActivityListener<MainActivityListener>()?.onLocationResult(data)
        }

    }

    @AfterPermissionGranted(value = RC_LOCATION)
    private fun getLocationWithPermission() {
        val fineLocation = Manifest.permission.ACCESS_FINE_LOCATION
        val coarseLocation = Manifest.permission.ACCESS_COARSE_LOCATION
        context?.let {
            if (EasyPermissions.hasPermissions(it, fineLocation, coarseLocation)) {
                // get location
                viewModel.getLocation()
            } else {
                EasyPermissions.requestPermissions(
                    this,
                    "Granted for location",
                    RC_LOCATION,
                    fineLocation, coarseLocation
                )
            }
        }
    }

    override fun onMessageFromActivity(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}