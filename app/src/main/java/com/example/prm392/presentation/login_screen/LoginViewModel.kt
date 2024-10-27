package com.example.prm392.presentation.login_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.prm392.data.dto.Users.GetUserAuth.UserResponseModel
import com.example.prm392.data.dto.Users.GetUserAuth.toGetAuthResponse
import com.example.prm392.data.dto.Users.toLoginResponseDto
import com.example.prm392.domain.model.User.Request.LoginRequestModel
import com.example.prm392.domain.model.User.Response.GetUserAuthResponseDto
import com.example.prm392.domain.model.User.Response.LoginResponseDto
import com.example.prm392.domain.model.User.Response.RegisterResponseDto
import com.example.prm392.domain.service.User.UserService
import com.example.prm392.presentation.navigation.Navigator
import com.example.prm392.presentation.navigation.Screen
import com.example.prm392.utils.Result
import com.example.prm392.utils.TokenSlice
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userService: UserService,
    private val tokenSlice: TokenSlice,
    private val navigator: Navigator
):ViewModel() {
    private val _loginServiceResponse = MutableStateFlow<Result<String>>(Result.Idle)
    val loginService = _loginServiceResponse.asStateFlow()

    private val _registerServiceResponse = MutableStateFlow<Result<RegisterResponseDto>>(Result.Idle)
    val registerServiceResponse = _registerServiceResponse.asStateFlow()

    private val _getUserServiceResponse = MutableStateFlow<Result<GetUserAuthResponseDto>>(Result.Idle)
    val getUserServiceResponse = _getUserServiceResponse.asStateFlow()

    fun onClickButtonLogin(requestModel: LoginRequestModel) = viewModelScope.launch {
        userService.loginService(requestModel)
            .onStart {
                _loginServiceResponse.value = Result.Loading
            }
            .catch {
                _loginServiceResponse.value = Result.Error(it)
            }
            .collect{
                val result = it.toLoginResponseDto()
                tokenSlice.saveToken(result.accessToken)
                navigator.navigate(Screen.SearchScreen)
                _loginServiceResponse.value = Result.Success<String>("")
            }
    }

    fun onClickButtonGetAuthUser() = viewModelScope.launch {
        userService.getAuthUserService()
            .onStart {
                _getUserServiceResponse.value = Result.Loading
            }
            .catch {
                _getUserServiceResponse.value = Result.Error(it)
            }
            .collect{
                val result = it.toGetAuthResponse()
                _getUserServiceResponse.value = Result.Success<GetUserAuthResponseDto>(result)
            }
    }

    fun onClickButtonLogout() = viewModelScope.launch {
        tokenSlice.clearToken()
        navigator.navigate(Screen.LoginScreen)
    }
}