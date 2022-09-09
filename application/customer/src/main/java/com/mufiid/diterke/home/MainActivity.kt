package com.mufiid.diterke.home

import android.location.Location
import android.os.Bundle
import android.widget.Toast
import com.mufiid.core.extensions.toLatLng
import com.mufiid.diterke.databinding.ActivityMainBinding
import com.mufiid.utils.BindingActivity

class MainActivity : BindingActivity<ActivityMainBinding>(), MainActivityListener {
    override fun inflateBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreateBinding(savedInstanceState: Bundle?) {
        val homeFragment = HomeFragment()

        supportFragmentManager.beginTransaction()
            .add(binding.mainFrame.id, homeFragment)
            .commit()
    }

    private fun locationResult(data: Location) {
        Toast.makeText(this, data.toLatLng().toString(), Toast.LENGTH_SHORT).show()
    }

    override fun onLocationResult(data: Location) {
        locationResult(data)
    }
}