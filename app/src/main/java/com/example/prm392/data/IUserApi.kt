package com.example.prm392.data

import com.example.prm392.data.dto.Users.LoginResponseModel
import com.example.prm392.data.dto.Users.GetUserAuth.UserResponseModel
import com.example.prm392.domain.model.User.Request.LoginRequestModel
import com.example.prm392.domain.model.User.Request.RegisterRequestModel
import com.example.prm392.domain.model.User.Response.RegisterResponseDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.POST

interface IUserApi {

    @POST("login")
    suspend fun login(
        @HeaderMap headers: Map<String,String>,
        @Body loginRequestModel: LoginRequestModel
    ) : LoginResponseModel

    @GET("refresh-token")
    suspend fun getAuth(
        @HeaderMap header: Map<String,String>,
    ) : UserResponseModel

    @POST("register")
    suspend fun register(
        @HeaderMap headers: Map<String,String>,
        @Body data: RegisterRequestModel
    ) : RegisterResponseDto
}