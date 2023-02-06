package com.alibasoglu.ciftlikpazarimandroid.adverts.ui.advertdetails

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.alibasoglu.ciftlikpazarimandroid.R
import com.alibasoglu.ciftlikpazarimandroid.core.fragment.BaseFragment
import com.alibasoglu.ciftlikpazarimandroid.core.fragment.FragmentConfiguration
import com.alibasoglu.ciftlikpazarimandroid.core.fragment.ToolbarConfiguration
import com.alibasoglu.ciftlikpazarimandroid.databinding.FragmentAdvertDetailsBinding
import com.alibasoglu.ciftlikpazarimandroid.utils.decodeBase64Image
import com.alibasoglu.ciftlikpazarimandroid.utils.viewbinding.viewBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AdvertDetailsFragment : BaseFragment(R.layout.fragment_advert_details) {

    private val toolbarConfiguration = ToolbarConfiguration(
        titleResId = R.string.advert_details,
        startIconResId = R.drawable.ic_baseline_arrow_back_24,
        startIconClick = ::navBack
    )

    private val binding by viewBinding(FragmentAdvertDetailsBinding::bind)

    private val advertDetailsViewModel by viewModels<AdvertDetailsViewModel>()

    override val fragmentConfiguration = FragmentConfiguration(toolbarConfiguration)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    private fun initUi() {
        activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)?.isVisible = false
        val advertDetails = advertDetailsViewModel.getAdvertDetails()
        val advertImageData = advertDetails.images[0]
        val price = "${advertDetails.price} TL"

        with(binding) {
            advertImageView.setImageBitmap(decodeBase64Image(advertImageData))
            advertNameTextView.text = advertDetails.name
            descriptionTextView.text = advertDetails.description
            cityTextView.text = advertDetails.city
            priceTextView.text = price
            categoryTextView.text = advertDetails.category

            phoneTextView.apply {
                // TODO add phone number after getting advert owners info
                text = "534 210 45 45"
                paint.isUnderlineText = true
                setOnClickListener {
                    makePhoneCall("05344749450")
                }
            }
            advertImageView.setOnClickListener {
                val extras = FragmentNavigatorExtras(advertImageView to TRANSITION_NAME)
                val bundle = Bundle()
                bundle.putString("advert_image_data", advertImageData)
                findNavController().navigate(
                    R.id.action_advertDetailsFragment_to_advertImageFragment,
                    bundle,
                    null,
                    extras
                )
            }
        }
    }

    private fun makePhoneCall(number: String) {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:$number")
        activity?.startActivity(intent)
    }

    companion object {
        const val TRANSITION_NAME = "image_big"
    }
}
