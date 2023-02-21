package com.alibasoglu.ciftlikpazarimandroid.user.ui.updateinfo

import androidx.lifecycle.viewModelScope
import com.alibasoglu.ciftlikpazarimandroid.core.BaseViewModel
import com.alibasoglu.ciftlikpazarimandroid.core.UserObject
import com.alibasoglu.ciftlikpazarimandroid.user.data.model.ChangePasswordRequest
import com.alibasoglu.ciftlikpazarimandroid.user.domain.UserRepository
import com.alibasoglu.ciftlikpazarimandroid.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
    private val userRepository: UserRepository
) : BaseViewModel() {

    private var _changePasswordState = MutableStateFlow<Resource<Unit>>(Resource.Loading(isLoading = false))
    val changePasswordState: StateFlow<Resource<Unit>>
        get() = _changePasswordState

    fun changePassword(newPassword: String) {
        val request = ChangePasswordRequest(id = UserObject.id, newPassword = newPassword)
        viewModelScope.launch {
            userRepository.changePassword(request).collectLatest { result ->
                when (result) {
                    is Resource.Success -> {
                        result.data?.let {
                            _changePasswordState.value = Resource.Success(data = null)
                        }
                    }
                    is Resource.Error -> {
                        _changePasswordState.value = Resource.Error(message = result.message ?: "Unknown Error")
                    }
                    is Resource.Loading -> {
                        _changePasswordState.value = Resource.Loading(isLoading = result.isLoading)
                    }
                }
            }
        }
    }

}
