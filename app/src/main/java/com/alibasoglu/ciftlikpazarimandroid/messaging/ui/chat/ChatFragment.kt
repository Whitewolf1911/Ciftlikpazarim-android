package com.alibasoglu.ciftlikpazarimandroid.messaging.ui.chat

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.unit.dp
import com.alibasoglu.ciftlikpazarimandroid.R
import com.alibasoglu.ciftlikpazarimandroid.core.fragment.BaseFragment
import com.alibasoglu.ciftlikpazarimandroid.core.fragment.FragmentConfiguration
import com.alibasoglu.ciftlikpazarimandroid.core.fragment.ToolbarConfiguration
import com.alibasoglu.ciftlikpazarimandroid.databinding.FragmentChatBinding
import com.alibasoglu.ciftlikpazarimandroid.messaging.model.Message
import com.alibasoglu.ciftlikpazarimandroid.utils.viewbinding.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ChatFragment : BaseFragment(R.layout.fragment_chat) {

    private val toolbarConfiguration = ToolbarConfiguration(
        titleResId = R.string.messaging,
        startIconResId = R.drawable.ic_baseline_arrow_back_24,
        startIconClick = ::navBack
    )

    override val fragmentConfiguration = FragmentConfiguration(toolbarConfiguration)

    private val binding by viewBinding(FragmentChatBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    private fun initUi() {
        hideBottomNavBar()
        binding.composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnLifecycleDestroyed(viewLifecycleOwner))
            setContent {
                val messages = remember { mutableStateListOf<Message>() }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xffFBE9E7))
                ) {
                    val scrollState = rememberLazyListState()
                    val coroutineScope = rememberCoroutineScope()

                    LazyColumn(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                        state = scrollState,
                        contentPadding = PaddingValues(top = 8.dp, bottom = 8.dp)
                    ) {
                        items(messages) { message ->
                            when (message.message.length) {
                                1 -> ReceivedMessageBox(text = message.message)
                                else -> SentMessageBox(text = message.message)
                            }
                        }
                    }
                    ChatInput(
                        onMessageChange = { messageContent ->
                            messages.add(Message("", "", messageContent))
                            coroutineScope.launch {
                                scrollState.animateScrollToItem(messages.size - 1)
                            }
                        }
                    )
                }
            }
        }
    }

}
