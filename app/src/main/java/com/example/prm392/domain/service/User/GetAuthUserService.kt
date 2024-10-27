package com.example.prm392.domain.service.User

import com.example.prm392.data.dto.Users.GetUserAuth.UserResponseModel
import com.example.prm392.data.repository.UserRepository
import com.example.prm392.domain.model.User.Response.GetUserAuthResponseDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAuthUserService @Inject constructor(
    private val iUserRepository: UserRepository
) {
    suspend operator fun invoke() : Flow<UserResponseModel> = flow {
        emit(iUserRepository.getAuth())
    }
}