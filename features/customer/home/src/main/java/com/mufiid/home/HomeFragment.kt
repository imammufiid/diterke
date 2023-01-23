package com.mufiid.home

import android.Manifest
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.mufiid.core.extensions.onSuccess
import com.mufiid.core.extensions.toLatLng
import com.mufiid.core.extensions.toLocation
import com.mufiid.core.stateevent.StateEventSubscriber
import com.mufiid.core.view.base.BindingFragment
import com.mufiid.core.view.component.WidgetInputLocationView
import com.mufiid.home.databinding.FragmentHomeBinding
import com.mufiid.locationapi.data.model.entity.LocationData
import com.mufiid.utils.isGrantedLocation
import com.mufiid.utils.listener.findActivityListener
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

    override fun onStart() {
        super.onStart()
        viewModel.subscribeLocation(subscriberLocation)
    }

    private fun getActivityListener(): MainActivityListener? {
        return findActivityListener()
    }

    override fun onCreateBinding(savedInstanceState: Bundle?) {
        val mapFragment = childFragmentManager
            .findFragmentById(R.id.map_view) as SupportMapFragment
        mapFragment.getMapAsync {
            map = it
        }

        subscribeLocationFrom()
        subscribeLocationDest()
        subscribeInitialLocation()
        subscribeThrowable()

        binding.inputCardView.onFromClick {
            getActivityListener()?.navigateToSearchLocation(MainActivityListener.FormType.FROM)
        }

        binding.inputCardView.onDestClick {
            getActivityListener()?.navigateToSearchLocation(MainActivityListener.FormType.DEST)
        }
    }

    private val subscriberLocation
        get() = object : StateEventSubscriber<Location> {
            override fun onIdle() {
                println("--- EVENT-LOCATION => OnIdle")
            }

            override fun onLoading() {
                println("--- EVENT-LOCATION => OnLoading")
            }

            override fun onFailure(throwable: Throwable) {
                println("--- EVENT-LOCATION => OnFailure: ${throwable.message}")
            }

            override fun onSuccess(data: Location) {
                println("--- EVENT-LOCATION => onSuccess: $data")
                val cameraUpdate = CameraUpdateFactory.newLatLngZoom(data.toLatLng(), 14f)
                map.animateCamera(cameraUpdate)
                getActivityListener()?.onLocationResult(data)
            }

        }

    private fun subscribeLocationFrom() {
        viewModel.locationFrom.observe(this) {
            it.onSuccess {
                val locationData = WidgetInputLocationView.InputLocationData(
                    location = this.latLng.toLocation(),
                    name = this.address
                )
            }
        }
    }

    private fun subscribeLocationDest() {
        viewModel.locationDestination.observe(this) {
            it.onSuccess {
                val locationData = WidgetInputLocationView.InputLocationData(
                    location = this.latLng.toLocation(),
                    name = this.address
                )
            }
        }
    }

    private fun subscribeInitialLocation() {
        viewModel.initialLocation.observe(this) {
            it.onSuccess {
                getActivityListener()?.sendFromLocation(this)
            }
        }
    }

    private fun subscribeThrowable() {
        viewModel.throwableHandler.observe(this) {
            if (it != null) {
                Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                binding.inputCardView.inputLocationFromData =
                    WidgetInputLocationView.locationDataFail()
                binding.inputCardView.inputLocationDestData =
                    WidgetInputLocationView.locationDataFail()
            }
        }
    }

    private fun actionFromActivity(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    @AfterPermissionGranted(value = RC_LOCATION)
    private fun getLocationWithPermission() {
        if (context?.isGrantedLocation() == false) {
            requestPermission()
        } else {
            viewModel.getLocation()
        }
    }

    private fun requestPermission() {
        locationPermissionRequest.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                viewModel.getLocation()
            }
            permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                requestPermission()
            }
            else -> {
                requestPermission()
            }
        }
    }

    override fun onMessageFromActivity(message: String) {
        actionFromActivity(message)
    }

    override fun requestLocation() {
        getLocationWithPermission()
    }

    override fun requestInitialData(location: Location) {
        viewModel.getInitialLocation(location)
    }

    override fun onDataLocation(from: LocationData, destination: LocationData) {
        binding.inputCardView.inputLocationFromData = WidgetInputLocationView.InputLocationData(
            location = from.latLng.toLocation(),
            name = from.address.ifEmpty {
                "Select location"
            }
        )

        binding.inputCardView.inputLocationDestData = WidgetInputLocationView.InputLocationData(
            location = destination.latLng.toLocation(),
            name = destination.address.ifEmpty {
                "Select location"
            }
        )
    }

    override fun pushLoadingFormLocation() {
        binding.inputCardView.inputLocationFromData = WidgetInputLocationView.locationDataLoading()
        binding.inputCardView.inputLocationDestData = WidgetInputLocationView.locationDataLoading()
    }
}