package com.example.prm392.domain.repository

import com.example.prm392.data.dto.Users.GetUserAuth.UserResponseModel
import com.example.prm392.data.dto.Users.GetUserProfile.GetUserProfileResponseModel
import com.example.prm392.data.dto.Users.LoginResponseModel
import com.example.prm392.domain.model.User.Request.LoginRequestModel
import com.example.prm392.domain.model.User.Request.RegisterRequestModel
import com.example.prm392.domain.model.User.Response.RegisterResponseDto

interface IUserRepository {
    suspend fun login(requestModel: LoginRequestModel) : LoginResponseModel
    suspend fun register(data : RegisterRequestModel) : RegisterResponseDto
    suspend fun getAuth() : UserResponseModel
    suspend fun getUserProfile(): GetUserProfileResponseModel
}