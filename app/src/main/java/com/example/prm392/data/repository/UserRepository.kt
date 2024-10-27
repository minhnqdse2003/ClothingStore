package com.example.prm392.data.repository

import com.example.prm392.data.IUserApi
import com.example.prm392.data.dto.Users.GetUserAuth.UserResponseModel
import com.example.prm392.data.dto.Users.LoginResponseModel
import com.example.prm392.domain.model.User.Request.LoginRequestModel
import com.example.prm392.domain.model.User.Request.RegisterRequestModel
import com.example.prm392.domain.model.User.Response.RegisterResponseDto
import com.example.prm392.domain.repository.IUserRepository
import com.example.prm392.utils.HeaderProcessing
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val api: IUserApi,
    private val headerProcessing: HeaderProcessing
    ) : IUserRepository {

    override suspend fun login(requestModel: LoginRequestModel): LoginResponseModel {
        return withContext(Dispatchers.Default) {
            val header = headerProcessing.createDynamicHeaders(
                isTokenIncluded = false
            )
            api.login(header, requestModel)
        }
    }

    override suspend fun register(data: RegisterRequestModel): RegisterResponseDto {
        return withContext(Dispatchers.Default) {
            val header = headerProcessing.createDynamicHeaders(
                isTokenIncluded = false
            )
            api.register(header, data)
        }
    }

    override suspend fun getAuth(): UserResponseModel {
        return withContext(Dispatchers.Default) {
            val header = headerProcessing.createDynamicHeaders(
                isTokenIncluded = true
            )
            api.getAuth(header)
        }
    }
}