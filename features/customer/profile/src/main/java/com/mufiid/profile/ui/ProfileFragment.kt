package com.mufiid.profile.ui

import android.os.Bundle
import com.mufiid.core.view.base.BindingFragment
import com.mufiid.profile.databinding.FragmentProfileBinding

class ProfileFragment : BindingFragment<FragmentProfileBinding>() {
    override fun inflateBinding(): FragmentProfileBinding {
        return FragmentProfileBinding.inflate(layoutInflater)
    }

    override fun onCreateBinding(savedInstanceState: Bundle?) {
    }

}