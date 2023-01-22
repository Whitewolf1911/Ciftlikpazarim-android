package com.alibasoglu.ciftlikpazarimandroid.adverts.ui

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.alibasoglu.ciftlikpazarimandroid.R
import com.alibasoglu.ciftlikpazarimandroid.core.fragment.BaseFragment
import com.alibasoglu.ciftlikpazarimandroid.core.fragment.FragmentConfiguration
import com.alibasoglu.ciftlikpazarimandroid.core.fragment.ToolbarConfiguration
import com.alibasoglu.ciftlikpazarimandroid.databinding.FragmentNewAdvertBinding
import com.alibasoglu.ciftlikpazarimandroid.utils.Resource
import com.alibasoglu.ciftlikpazarimandroid.utils.decodeBase64Image
import com.alibasoglu.ciftlikpazarimandroid.utils.encodeImageToBase64
import com.alibasoglu.ciftlikpazarimandroid.utils.lifecycle.observe
import com.alibasoglu.ciftlikpazarimandroid.utils.viewbinding.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NewAdvertFragment : BaseFragment(R.layout.fragment_new_advert) {

    private val toolbarConfiguration = ToolbarConfiguration(R.string.add_advert)

    override val fragmentConfiguration = FragmentConfiguration(toolbarConfiguration = toolbarConfiguration)

    private val binding by viewBinding(FragmentNewAdvertBinding::bind)

    private val newAdvertViewModel by viewModels<NewAdvertViewModel>()

    private val newAdvertStateCollector = FlowCollector<Resource<Unit>> { value ->
        processNewAdvertState(value)
    }

    private lateinit var pickMedia: ActivityResultLauncher<PickVisualMediaRequest>

    private var base64Image: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initImagePicker()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        initObservers()
    }

    private fun initUi() {
        // TODO add validation
        with(binding) {
            advertImageView.setOnClickListener {
                selectImage()
            }
            addNewAdvertButton.setOnClickListener {
                lifecycleScope.launch {
                    addNewAdvert()
                }
            }
            // Init city spinner
            ArrayAdapter.createFromResource(
                requireContext(),
                R.array.cities,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                citySpinner.adapter = adapter
            }

            // Init category spinner
            ArrayAdapter.createFromResource(
                requireContext(),
                R.array.categories,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                categorySpinner.adapter = adapter
            }

        }
    }

    private fun initImagePicker() {
        pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            // Callback is invoked after the user selects a media item or closes the
            // photo picker.
            if (uri != null) {
                try {
                    binding.advertImageView.setImageURI(uri)
                    val drawableImg = binding.advertImageView.drawable

                    val bitmapImg = drawableImg.toBitmap()
                    base64Image = encodeImageToBase64(bitmapImg, Bitmap.CompressFormat.JPEG, 1)

                    val decodedBitmap = decodeBase64Image(base64Image)
                    binding.advertImageView.setImageBitmap(decodedBitmap)

                    binding.pickPhotoTextView.isVisible = false
                } catch (e: Exception) {
                    Log.d("exception", "Exception occurred")
                }
            } else {
                Log.d("PhotoPicker", "No media selected")
            }
        }
    }

    private fun selectImage() {
        pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private fun initObservers() {
        viewLifecycleOwner.observe {
            newAdvertViewModel.newAdvertsStateFlow.collect(newAdvertStateCollector)
        }
    }

    private fun processNewAdvertState(value: Resource<Unit>) {
        when (value) {
            is Resource.Success -> {
                Toast.makeText(context, "İlanınız onaylandıktan sonra yayınlanacaktır.", Toast.LENGTH_SHORT).show()
            }
            is Resource.Error -> {
                Toast.makeText(context, value.message, Toast.LENGTH_SHORT).show()
            }
            is Resource.Loading -> {
                if (value.isLoading) {
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        }
    }

    private suspend fun addNewAdvert() {
        with(binding) {
            val price = if (priceEditText.text.isNullOrBlank()) {
                0
            } else {
                priceEditText.text.toString().toInt()
            }
            newAdvertViewModel.addNewAdvert(
                name = advertTitleEditText.text.toString(),
                description = advertDescriptionEditText.text.toString(),
                images = listOf(base64Image.toString()),
                price = price,
                category = categorySpinner.selectedItem as String,
                city = citySpinner.selectedItem as String,
                userId = "userid123"
            )
        }
    }

}
