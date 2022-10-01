package com.mufiid.diterke.home

import android.content.Context
import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.widget.Toast
import com.mufiid.core.extensions.attachFragment
import com.mufiid.core.extensions.toLatLng
import com.mufiid.diterke.databinding.ActivityMainBinding
import com.mufiid.searchlocation.ui.SearchLocationActivity
import com.mufiid.core.view.base.BindingActivity

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