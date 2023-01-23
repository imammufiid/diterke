package com.mufiid.searchlocation.ui

import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import com.mufiid.core.extensions.replaceFragment
import com.mufiid.core.extensions.toLocation
import com.mufiid.core.stateevent.StateEventSubscriber
import com.mufiid.core.view.base.BindingFragment
import com.mufiid.core.view.component.WidgetInputLocationView
import com.mufiid.locationapi.data.model.entity.LocationData
import com.mufiid.navigation.FragmentConnector
import com.mufiid.searchlocation.databinding.FragmentSearchLocationBinding
import com.mufiid.utils.snackBar
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchLocationFragment : BindingFragment<FragmentSearchLocationBinding>() {
    private val viewModel: SearchLocationViewModel by viewModel()

    override fun inflateBinding(): FragmentSearchLocationBinding {
        return FragmentSearchLocationBinding.inflate(layoutInflater)
    }

    private val formType: Int by lazy {
        arguments?.getInt("formType", 1) ?: 1
    }


    private val fromLocationExtra by lazy {
        arguments?.getParcelable("location_from") ?: LocationData()
    }

    private val destLocationExtra by lazy {
        arguments?.getParcelable("location_dest") ?: LocationData()
    }

    override fun onCreateBinding(savedInstanceState: Bundle?) {
        Toast.makeText(context, formType.toString(), Toast.LENGTH_SHORT).show()
        binding.inputSearch.setFocus(formType)
        binding.btnSearch.setOnClickListener {
            // viewModel.searchLocation(binding.etSearch.text.toString())
        }

        binding.btnProfile.setOnClickListener {
            val profileFragment = FragmentConnector.Profile.profileFragment
            childFragmentManager.replaceFragment(binding.frameLayout, profileFragment)
        }

        viewModel.fromLocation = fromLocationExtra
        viewModel.destLocation = destLocationExtra

        binding.snackBar(viewModel.fromLocation.name)
        if (viewModel.fromLocation.latLng.latitude != 0.0) {
            binding.inputSearch.inputLocationFromData = WidgetInputLocationView.InputLocationData(
                location = viewModel.fromLocation.latLng.toLocation(),
                name = viewModel.fromLocation.address
            )
        }

        if (viewModel.destLocation.latLng.latitude != 0.0) {
            binding.inputSearch.inputLocationDestData = WidgetInputLocationView.InputLocationData(
                location = viewModel.destLocation.latLng.toLocation(),
                name = viewModel.destLocation.address
            )
        }


        viewModel.subscribeSearchLocation(subscriberSearchLocation)
    }

    private val subscriberSearchLocation
        get() = object : StateEventSubscriber<List<LocationData>> {
            override fun onIdle() {
                binding.progbar.isVisible = false
            }

            override fun onLoading() {
                binding.progbar.isVisible = true
            }

            override fun onFailure(throwable: Throwable) {
                binding.progbar.isVisible = false
                binding.txtResult.text = throwable.message
            }

            override fun onSuccess(data: List<LocationData>) {
                binding.progbar.isVisible = false
                binding.txtResult.text = data.map { l -> l.name }.toString()
            }

        }
}