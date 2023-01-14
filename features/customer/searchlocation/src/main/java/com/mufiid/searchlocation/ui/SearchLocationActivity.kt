package com.mufiid.searchlocation.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.view.isVisible
import com.mufiid.core.stateevent.StateEventSubscriber
import com.mufiid.core.view.base.BindingActivity
import com.mufiid.locationapi.data.model.entity.LocationData
import com.mufiid.searchlocation.databinding.ActivitySearchLocationBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchLocationActivity : BindingActivity<ActivitySearchLocationBinding>() {
    private val viewModel: SearchLocationViewModel by viewModel()

    companion object {
        fun launch(context: Context) {
            context.startActivity(
                Intent(context, SearchLocationActivity::class.java)
            )
        }
    }

    override fun inflateBinding(): ActivitySearchLocationBinding {
        return ActivitySearchLocationBinding.inflate(layoutInflater)
    }

    override fun onCreateBinding(savedInstanceState: Bundle?) {
        binding.btnSearch.setOnClickListener {
            viewModel.searchLocation(binding.etSearch.text.toString())
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