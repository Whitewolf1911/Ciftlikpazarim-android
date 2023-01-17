package com.alibasoglu.ciftlikpazarimandroid.adverts.ui

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.alibasoglu.ciftlikpazarimandroid.R
import com.alibasoglu.ciftlikpazarimandroid.core.fragment.BaseFragment
import com.alibasoglu.ciftlikpazarimandroid.core.fragment.FragmentConfiguration
import com.alibasoglu.ciftlikpazarimandroid.core.fragment.ToolbarConfiguration
import com.alibasoglu.ciftlikpazarimandroid.databinding.FragmentNewAdvertBinding
import com.alibasoglu.ciftlikpazarimandroid.utils.Resource
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

    private var selectedImageUri: Uri? = null

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
        }
    }

    private fun initImagePicker() {
        pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            // Callback is invoked after the user selects a media item or closes the
            // photo picker.
            if (uri != null) {
                Log.d("PhotoPicker", "Selected URI: $uri")
                binding.advertImageView.setImageURI(uri)
                selectedImageUri = uri
                binding.pickPhotoTextView.isVisible = false
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
                Toast.makeText(context, "İlan ekleme başarılı", Toast.LENGTH_SHORT).show()
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
                images = listOf("base64 photo"),
                price = price,
                category = "Büyükbaş Hayvanlar",
                city = "Denizli",
                userId = "userid123"
            )
        }
    }

}
