package com.example.prm392.domain.service.NotifyService

import android.util.Log
import com.example.prm392.data.dto.Notify.GetNotifyResponseModel
import com.example.prm392.domain.repository.INotifyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdateStatus @Inject constructor(
    private val repository: INotifyRepository

) {
    suspend operator fun invoke(
        notificationId : Int
    ): Flow<Unit> = flow {
        try {
            repository.updateStatus(notificationId)
            emit(Unit)
        } catch (e: Exception) {
            throw e
        }
    }
}