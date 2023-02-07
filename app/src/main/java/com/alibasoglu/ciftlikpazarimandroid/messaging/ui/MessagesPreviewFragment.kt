package com.alibasoglu.ciftlikpazarimandroid.messaging.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.alibasoglu.ciftlikpazarimandroid.R
import com.alibasoglu.ciftlikpazarimandroid.core.fragment.BaseFragment
import com.alibasoglu.ciftlikpazarimandroid.core.fragment.FragmentConfiguration
import com.alibasoglu.ciftlikpazarimandroid.core.fragment.ToolbarConfiguration
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MessagesPreviewFragment : BaseFragment(R.layout.fragment_messages_preview) {

    private val toolbarConfiguration = ToolbarConfiguration(titleResId = R.string.messages)

    override val fragmentConfiguration = FragmentConfiguration(toolbarConfiguration)

    private val messagesPreviewViewModel by viewModels<MessagesPreviewViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        initObservers()
        messagesPreviewViewModel.getMessagesPreview()
    }


    private fun initUi() {
        // TODO initUi
    }

    private fun initObservers() {
        // TODO init observers
    }

    private fun navToMessageDetailsFragment() {
        // TODO nav to conversation
    }

}
