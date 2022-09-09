package com.mufiid.diterke.home

import android.os.Bundle
import com.mufiid.diterke.databinding.ActivityMainBinding
import com.mufiid.utils.BindingActivity

class MainActivity : BindingActivity<ActivityMainBinding>() {
    override fun inflateBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreateBinding(savedInstanceState: Bundle?) {
        val homeFragment = HomeFragment()

        supportFragmentManager.beginTransaction()
            .add(binding.mainFrame.id, homeFragment)
            .commit()
    }
}