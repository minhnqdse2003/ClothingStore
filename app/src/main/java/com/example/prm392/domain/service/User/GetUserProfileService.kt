package com.example.prm392.domain.service.User

import com.example.prm392.data.dto.Users.GetUserProfile.GetUserProfileResponseModel
import com.example.prm392.data.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetUserProfileService @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke() : Flow<GetUserProfileResponseModel> = flow {
        emit(repository.getUserProfile())
    }
}