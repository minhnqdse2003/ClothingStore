package com.example.prm392.domain.service.User

import com.example.prm392.data.dto.ProductDataDto
import com.example.prm392.domain.model.User.Request.RegisterRequestModel
import com.example.prm392.domain.model.User.Response.RegisterResponseDto
import com.example.prm392.domain.repository.IUserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RegisterService @Inject constructor(
    private val repository: IUserRepository
) {
    suspend operator fun invoke(requestModel: RegisterRequestModel) : Flow<RegisterResponseDto> = flow {
        emit(repository.register(requestModel))
    }
}