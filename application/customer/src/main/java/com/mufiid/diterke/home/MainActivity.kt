package com.mufiid.diterke.home

import android.location.Location
import android.os.Bundle
import android.widget.Toast
import com.mufiid.core.extensions.attachFragment
import com.mufiid.core.extensions.toLatLng
import com.mufiid.diterke.databinding.ActivityMainBinding
import com.mufiid.locationapi.ui.SearchLocationActivity
import com.mufiid.utils.BindingActivity
import com.mufiid.utils.listener.findFragmentListener

class MainActivity : BindingActivity<ActivityMainBinding>(), MainActivityListener {
    override fun inflateBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    private lateinit var tagHomeFragment: String

    override fun onCreateBinding(savedInstanceState: Bundle?) {
        val homeFragment = HomeFragment()
        tagHomeFragment = attachFragment(binding.mainFrame, homeFragment::class)
        binding.btnSearch.setOnClickListener {
            SearchLocationActivity.launch(this)
        }
    }

    private fun locationResult(data: Location) {
        println("DATA-RESULT => $data")
         Toast.makeText(this, data.toLatLng().toString(), Toast.LENGTH_SHORT).show()
    }

    override fun onLocationResult(data: Location) {
        /**
         * get data from fragment to it activity
         */
        locationResult(data)
    }
}