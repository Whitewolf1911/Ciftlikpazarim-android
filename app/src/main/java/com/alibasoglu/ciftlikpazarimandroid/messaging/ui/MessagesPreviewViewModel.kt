package com.alibasoglu.ciftlikpazarimandroid.messaging.ui

import androidx.lifecycle.viewModelScope
import com.alibasoglu.ciftlikpazarimandroid.core.BaseViewModel
import com.alibasoglu.ciftlikpazarimandroid.messaging.domain.MessagesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class MessagesPreviewViewModel @Inject constructor(
    private val messagesRepository: MessagesRepository
) : BaseViewModel() {

    init {

    }

    fun getMessagesPreview() {
        viewModelScope.launch {
        }
    }

}
