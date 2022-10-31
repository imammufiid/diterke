package com.mufiid.searchlocation.ui

import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import com.mufiid.core.extensions.attachFragment
import com.mufiid.core.extensions.replaceFragment
import com.mufiid.core.state.StateEventSubscriber
import com.mufiid.core.view.base.BindingFragment
import com.mufiid.locationapi.data.model.entity.LocationData
import com.mufiid.navigation.FragmentConnector
import com.mufiid.navigation.ProfileFragmentConnector
import com.mufiid.searchlocation.databinding.FragmentSearchLocationBinding
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchLocationFragment : BindingFragment<FragmentSearchLocationBinding>() {
    private val viewModel: SearchLocationViewModel by viewModel()

    override fun inflateBinding(): FragmentSearchLocationBinding {
        return FragmentSearchLocationBinding.inflate(layoutInflater)
    }

    private val formType: Int by lazy {
        arguments?.getInt("formType", 1) ?: 1
    }


    override fun onCreateBinding(savedInstanceState: Bundle?) {
        Toast.makeText(context, formType.toString(), Toast.LENGTH_SHORT).show()
        binding.btnSearch.setOnClickListener {
            viewModel.searchLocation(binding.etSearch.text.toString())
        }

        binding.btnProfile.setOnClickListener {
            val profileFragment = FragmentConnector.Profile.profileFragment
            childFragmentManager.replaceFragment(binding.frameLayout, profileFragment)
        }

        viewModel.subscribe(object : StateEventSubscriber<List<LocationData>> {
            override fun onIdle() {
                binding.progressBar.isVisible = false
            }

            override fun onLoading() {
                binding.progressBar.isVisible = true
            }

            override fun onFailure(throwable: Throwable) {
                binding.progressBar.isVisible = false
                binding.tvLocationList.text = throwable.message
            }

            override fun onSuccess(data: List<LocationData>) {
                binding.progressBar.isVisible = false
                binding.tvLocationList.text = data.map { l -> l.name }.toString()
            }

        })
    }
}