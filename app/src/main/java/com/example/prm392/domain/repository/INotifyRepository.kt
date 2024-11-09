package com.example.prm392.domain.repository

import com.example.prm392.data.dto.Notify.GetNotifyResponseModel

interface INotifyRepository {
    suspend fun getNotifyById(id: Int): List<GetNotifyResponseModel>
    suspend fun updateStatus(notificationId: Int)
    suspend fun createNoti(userId: Int, message: String)
}