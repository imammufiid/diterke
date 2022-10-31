package com.mufiid.home

import android.Manifest
import android.location.Location
import android.os.Bundle
import android.widget.Toast
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.mufiid.core.extensions.onSuccess
import com.mufiid.core.extensions.toLatLng
import com.mufiid.core.extensions.toLocation
import com.mufiid.core.state.StateEventSubscriber
import com.mufiid.core.view.base.BindingFragment
import com.mufiid.core.view.component.WidgetInputLocationView
import com.mufiid.home.databinding.FragmentHomeBinding
import com.mufiid.locationapi.data.model.entity.LocationData
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
        viewModel.subscribeLocation(locationSubscriber())
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

    private val subscriberLocation = object : StateEventSubscriber<Location> {
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
        }

    }

    private fun locationSubscriber() = object : StateEventSubscriber<Location> {
        override fun onIdle() {
            println("----- location idle")
        }

        override fun onLoading() {
            println("----- location loading")
        }

        override fun onFailure(throwable: Throwable) {
            println("----- location failure -> ${throwable.message}")
        }

        override fun onSuccess(data: Location) {
            println("----- location success -> $data")

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