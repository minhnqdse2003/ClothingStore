package com.example.prm392.domain.service.NotifyService

import android.util.Log
import com.example.prm392.data.dto.Notify.GetNotifyResponseModel
import com.example.prm392.domain.repository.INotifyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdateStatus @Inject constructor(
    private val repository: INotifyRepository

) {
    suspend operator fun invoke(
        notificationId : Int
    ): Flow<Result<Unit>> = flow {
            Log.d("Test1", "UpdateStatus")
            repository.updateStatus(notificationId)
            emit(Result.success(Unit))
        }.catch { e ->
            Log.d("UpdateStatus", "Error updating status for notification ID: $notificationId", e)
        }
    }