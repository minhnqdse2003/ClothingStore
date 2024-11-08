package com.example.prm392.domain.service.User

data class UserService (
    val loginService : LoginService,
    val registerService: RegisterService,
    val getAuthUserService: GetAuthUserService,
    val getUserProfile : GetUserProfileService
)