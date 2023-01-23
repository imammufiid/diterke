package com.mufiid.diterke

import android.content.Context
import android.content.Intent
import android.location.Location
import android.os.Bundle
import androidx.core.os.bundleOf
import com.mufiid.core.extensions.attachFragment
import com.mufiid.core.extensions.onFailure
import com.mufiid.core.extensions.replaceFragment
import com.mufiid.core.view.base.BindingActivity
import com.mufiid.diterke.databinding.ActivityMainBinding
import com.mufiid.home.HomeFragment
import com.mufiid.home.HomeFragmentListener
import com.mufiid.home.MainActivityListener
import com.mufiid.locationapi.data.model.entity.LocationData
import com.mufiid.navigation.ActivityConnector
import com.mufiid.searchlocation.ui.SearchLocationFragment
import com.mufiid.utils.listener.findFragmentListener
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BindingActivity<ActivityMainBinding>(), MainActivityListener {
    private lateinit var homeTag: String

    private var fromLocation: LocationData = LocationData()
    private var destLocation: LocationData = LocationData()

    private var currentLocation: Location? = null

    override fun inflateBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    private fun getFragmentListener(): HomeFragmentListener? {
        return findFragmentListener(homeTag)
    }

    private val mainViewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel.getCurrentUser()
        mainViewModel.userState.observe(this) { state ->
            println("OJEKU =======")
            println(state)
            println("OJEKU =======")
            state.onFailure {
                ActivityConnector.customerActivity.toAuthActivity(this@MainActivity)
                finish()
            }
        }
    }

    override fun onCreateBinding(savedInstanceState: Bundle?) {
        homeTag = supportFragmentManager.attachFragment(binding.mainFrame, HomeFragment::class)
        binding.btnSearch.setOnClickListener {
            navigateToSearchFragment()
        }

        MainScope().launch {
            delay(1000)
            getFragmentListener()?.pushLoadingFormLocation()
            getFragmentListener()?.requestLocation()
        }
    }

    private fun navigateToSearchFragment() {
        supportFragmentManager.replaceFragment(binding.mainFrame, SearchLocationFragment::class)
    }

    /**
     * get data from fragment to this activity
     */
    override fun onLocationResult(data: Location) {
        if (currentLocation != data) {
            currentLocation = data
            getFragmentListener()?.requestInitialData(data)
        }
    }

    override fun sendFromLocation(from: LocationData) {
        fromLocation = from
        updateLocationData()
    }

    override fun sendDestinationLocation(destination: LocationData) {
        destLocation = destination
        updateLocationData()
    }

    override fun navigateToSearchLocation(formType: Int) {
        val bundleForm = bundleOf(
            "formType" to formType,
            "location_from" to fromLocation,
            "location_dest" to destLocation
        )
        supportFragmentManager.replaceFragment(
            binding.mainFrame,
            SearchLocationFragment::class,
            bundle = bundleForm
        )
    }

    private fun updateLocationData() {
        getFragmentListener()?.onDataLocation(fromLocation, destLocation)
    }

    companion object {
        fun launch(context: Context) {
            context.startActivity(Intent(context, MainActivity::class.java))
        }
    }
}