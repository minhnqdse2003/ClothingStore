package com.example.prm392.presentation.profile_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prm392.data.dto.Users.GetUserProfile.GetUserProfileResponseModel
import com.example.prm392.domain.service.User.UserService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import com.example.prm392.utils.Result
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userService: UserService
) : ViewModel() {
    private val _userProfile = MutableStateFlow<Result<GetUserProfileResponseModel>>(Result.Idle)
    val userProfile = _userProfile.asStateFlow()

    fun getUserProfile() {
        viewModelScope.launch {
            userService.getUserProfile()
                .onStart {
                    _userProfile.value = Result.Loading
                }
                .catch { exception ->
                    _userProfile.value = Result.Error(exception)
                }
                .collect { profile ->
                    _userProfile.value = Result.Success<GetUserProfileResponseModel>(profile)
                }
        }
    }
}