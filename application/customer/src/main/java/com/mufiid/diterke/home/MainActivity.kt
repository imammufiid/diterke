package com.mufiid.diterke.home

import android.content.Context
import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.widget.Toast
import com.mufiid.core.extensions.attachFragment
import com.mufiid.core.extensions.replaceFragment
import com.mufiid.core.extensions.toLatLng
import com.mufiid.diterke.databinding.ActivityMainBinding
import com.mufiid.searchlocation.ui.SearchLocationActivity
import com.mufiid.core.view.base.BindingActivity
import com.mufiid.searchlocation.ui.SearchLocationFragment

class MainActivity : BindingActivity<ActivityMainBinding>(), MainActivityListener {
    override fun inflateBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }


    override fun onCreateBinding(savedInstanceState: Bundle?) {
        supportFragmentManager.attachFragment(binding.mainFrame, HomeFragment::class)
        binding.btnSearch.setOnClickListener {
            navigateToSearchFragment()
        }
    }

    private fun navigateToSearchFragment() {
        supportFragmentManager.replaceFragment(binding.mainFrame, SearchLocationFragment::class)
    }

    /**
     * get data from fragment to this activity
     */
    override fun onLocationResult(data: Location) {
        Toast.makeText(this, data.toLatLng().toString(), Toast.LENGTH_SHORT).show()
    }

    companion object {
        fun launch(context: Context) {
            context.startActivity(Intent(context, MainActivity::class.java))
        }
    }
}