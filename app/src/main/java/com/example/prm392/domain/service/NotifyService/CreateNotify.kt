package com.example.prm392.domain.service.NotifyService

import android.util.Log
import com.example.prm392.domain.repository.INotifyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CreateNotify @Inject constructor(
    private val repository: INotifyRepository

) {
    suspend operator fun invoke(
        userId: Int,
        message: String
    ): Flow<Result<Unit>> = flow  {
        repository.createNoti(userId, message)
        emit(Result.success(Unit))
    }.catch { e ->
        Log.d("Create Noti", "Error create noti for User ID: $userId", e)
    }
}