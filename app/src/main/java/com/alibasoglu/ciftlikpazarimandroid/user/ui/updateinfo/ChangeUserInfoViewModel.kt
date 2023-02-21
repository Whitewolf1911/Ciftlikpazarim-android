package com.alibasoglu.ciftlikpazarimandroid.user.ui.updateinfo

import androidx.lifecycle.viewModelScope
import com.alibasoglu.ciftlikpazarimandroid.core.BaseViewModel
import com.alibasoglu.ciftlikpazarimandroid.core.UserObject
import com.alibasoglu.ciftlikpazarimandroid.core.setUserObjectFromModel
import com.alibasoglu.ciftlikpazarimandroid.user.data.model.UpdateUserInfoRequest
import com.alibasoglu.ciftlikpazarimandroid.user.domain.UserRepository
import com.alibasoglu.ciftlikpazarimandroid.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@HiltViewModel
class ChangeUserInfoViewModel @Inject constructor(
    private val userRepository: UserRepository
) : BaseViewModel() {

    private var _updateUserInfoState = MutableStateFlow<Resource<Unit>>(Resource.Loading(isLoading = false))
    val updateUserInfoState: StateFlow<Resource<Unit>>
        get() = _updateUserInfoState

    fun updateUserInfo(name: String, phoneNumber: String, email: String) {
        val request =
            UpdateUserInfoRequest(id = UserObject.id, name = name, email = email, phoneNumber = phoneNumber)
        viewModelScope.launch {
            userRepository.updateUserInfo(request).collectLatest { result ->
                when (result) {
                    is Resource.Success -> {
                        result.data?.let { user ->
                            setUserObjectFromModel(user)
                            _updateUserInfoState.value = Resource.Success(data = null)
                        }
                    }
                    is Resource.Error -> {
                        _updateUserInfoState.value = Resource.Error(message = result.message ?: "Unknown Error")
                    }
                    is Resource.Loading -> {
                        _updateUserInfoState.value = Resource.Loading(isLoading = result.isLoading)
                    }
                }
            }
        }
    }
}
