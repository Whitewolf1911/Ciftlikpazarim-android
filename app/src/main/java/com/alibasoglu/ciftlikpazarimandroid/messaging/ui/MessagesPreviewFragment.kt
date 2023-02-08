package com.alibasoglu.ciftlikpazarimandroid.messaging.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import com.alibasoglu.ciftlikpazarimandroid.R
import com.alibasoglu.ciftlikpazarimandroid.core.fragment.BaseFragment
import com.alibasoglu.ciftlikpazarimandroid.core.fragment.FragmentConfiguration
import com.alibasoglu.ciftlikpazarimandroid.core.fragment.ToolbarConfiguration
import com.alibasoglu.ciftlikpazarimandroid.databinding.FragmentMessagesPreviewBinding
import com.alibasoglu.ciftlikpazarimandroid.messaging.model.MessagePreview
import com.alibasoglu.ciftlikpazarimandroid.utils.viewbinding.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MessagesPreviewFragment : BaseFragment(R.layout.fragment_messages_preview) {

    private val toolbarConfiguration = ToolbarConfiguration(titleResId = R.string.messages)

    override val fragmentConfiguration = FragmentConfiguration(toolbarConfiguration)

    private val binding by viewBinding(FragmentMessagesPreviewBinding::bind)

    private val messagesPreviewViewModel by viewModels<MessagesPreviewViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        initObservers()
        messagesPreviewViewModel.getMessagesPreview()
    }

    private fun initUi() {
        binding.composeView.apply {
            setContent {
                val dummyData =
                    MessagePreview(
                        id = "id",
                        username = "Ahmet Mehmet",
                        lastMessage = "Selamlar ustam bu traktor en son kaca olur? hadi bi cevap ustam"
                    )
                LazyColumn(contentPadding = PaddingValues(bottom = 80.dp) ) {
                    items(count = 10) {
                        MessagePreviewItem(
                            messagePreview = dummyData,
                            modifier = Modifier
                                .padding(12.dp)
                                .clickable {
                                    navToMessageDetailsFragment()
                                },
                        )
                    }
                }
            }
        }
    }

    private fun initObservers() {
        // TODO init observers
    }

    private fun navToMessageDetailsFragment() {
        Toast.makeText(context, "you clicked me", Toast.LENGTH_SHORT).show()
    }

}
