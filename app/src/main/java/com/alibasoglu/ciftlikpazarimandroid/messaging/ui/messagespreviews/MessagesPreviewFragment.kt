package com.alibasoglu.ciftlikpazarimandroid.messaging.ui.messagespreviews

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.viewModels
import com.alibasoglu.ciftlikpazarimandroid.R
import com.alibasoglu.ciftlikpazarimandroid.core.fragment.BaseFragment
import com.alibasoglu.ciftlikpazarimandroid.core.fragment.FragmentConfiguration
import com.alibasoglu.ciftlikpazarimandroid.core.fragment.ToolbarConfiguration
import com.alibasoglu.ciftlikpazarimandroid.databinding.FragmentMessagesPreviewBinding
import com.alibasoglu.ciftlikpazarimandroid.messaging.ui.ErrorComposable
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
    }

    override fun onResume() {
        super.onResume()
        showBottomNavBar()
        messagesPreviewViewModel.getMessagesPreview()
    }

    private fun initUi() {
        binding.composeView.apply {
            setViewCompositionStrategy(
                ViewCompositionStrategy.DisposeOnLifecycleDestroyed(viewLifecycleOwner)
            )
            setContent {
                val state = messagesPreviewViewModel.state

                if (state.error == null) {
                    LazyColumn(contentPadding = PaddingValues(bottom = 80.dp)) {
                        items(state.messagesPreviews.count()) { index ->
                            val previewItem = state.messagesPreviews[index]
                            MessagePreviewItem(
                                messagePreview = previewItem,
                                modifier = Modifier
                                    .padding(12.dp)
                                    .clickable { navToMessageDetailsFragment(previewItem.id) },
                            )
                        }
                    }
                }
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    if (state.isLoading) {
                        CircularProgressIndicator(color = Color.Green)
                    } else if (state.error != null) {
                        ErrorComposable(errorText = state.error)
                    } else if (state.messagesPreviews.isEmpty()) {
                        Text(
                            text = getString(R.string.messages_shown_here),
                            color = MaterialTheme.colors.onPrimary,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }

    private fun navToMessageDetailsFragment(id: String) {
        nav(MessagesPreviewFragmentDirections.actionMessagesPreviewFragmentToChatFragment(id))
    }

}
