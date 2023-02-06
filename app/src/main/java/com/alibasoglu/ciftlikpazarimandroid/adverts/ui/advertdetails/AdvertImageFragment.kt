package com.alibasoglu.ciftlikpazarimandroid.adverts.ui.advertdetails

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.View
import androidx.fragment.app.viewModels
import com.alibasoglu.ciftlikpazarimandroid.R
import com.alibasoglu.ciftlikpazarimandroid.core.fragment.BaseFragment
import com.alibasoglu.ciftlikpazarimandroid.core.fragment.FragmentConfiguration
import com.alibasoglu.ciftlikpazarimandroid.core.fragment.ToolbarConfiguration
import com.alibasoglu.ciftlikpazarimandroid.databinding.FragmentAdvertImageBinding
import com.alibasoglu.ciftlikpazarimandroid.utils.decodeBase64Image
import com.alibasoglu.ciftlikpazarimandroid.utils.viewbinding.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AdvertImageFragment : BaseFragment(R.layout.fragment_advert_image) {

    private val toolbarConfiguration = ToolbarConfiguration(
        titleResId = null,
        startIconResId = R.drawable.ic_baseline_arrow_back_24,
        startIconClick = ::navBack
    )

    override val fragmentConfiguration = FragmentConfiguration(toolbarConfiguration)

    private val binding by viewBinding(FragmentAdvertImageBinding::bind)

    private val advertImageViewModel by viewModels<AdvertImageViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val animation = TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.move)
        sharedElementEnterTransition = animation
        sharedElementReturnTransition = animation
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val advertImageData = advertImageViewModel.advertImageData
        binding.advertImageView.setImageBitmap(decodeBase64Image(advertImageData))
    }
}
