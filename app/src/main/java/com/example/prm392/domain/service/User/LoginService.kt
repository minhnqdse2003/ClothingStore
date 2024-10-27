package com.example.prm392.domain.service.User

import com.example.prm392.data.dto.Users.LoginResponseModel
import com.example.prm392.domain.model.User.Request.LoginRequestModel
import com.example.prm392.domain.repository.IUserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginService @Inject constructor(
    private val repository: IUserRepository
) {
    suspend operator fun invoke(requestModel: LoginRequestModel) : Flow<LoginResponseModel> = flow {
        emit(repository.login(requestModel))
    }
}