package com.example.prm392.data

import com.example.prm392.data.dto.Notify.GetNotifyResponseModel
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.PUT
import retrofit2.http.Query

interface INotifyAPI {
    @GET("api/Notification/user")
    suspend fun getNotify(
        @HeaderMap headers: Map<String, String>,
        @Query("userId") userId: Int = 71,
    ): List<GetNotifyResponseModel>

    @PUT("api/Notification/mark-as-read")
    suspend fun updateStatus(
        @HeaderMap headers: Map<String, String>,
        @Query("notificationId") notificationId: Int
        )

}