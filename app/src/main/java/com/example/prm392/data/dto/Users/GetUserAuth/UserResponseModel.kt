package com.example.prm392.data.dto.Users.GetUserAuth


import com.example.prm392.domain.model.User.Response.GetUserAuthResponseDto
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserResponseModel(
    @Json(name = "id")
    val id: Int,
    @Json(name = "firstName")
    val firstName: String,
    @Json(name = "lastName")
    val lastName: String,
    @Json(name = "maidenName")
    val maidenName: String,
    @Json(name = "age")
    val age: Int,
    @Json(name = "gender")
    val gender: String,
    @Json(name = "email")
    val email: String,
    @Json(name = "phone")
    val phone: String,
    @Json(name = "username")
    val username: String,
    @Json(name = "password")
    val password: String,
    @Json(name = "birthDate")
    val birthDate: String,
    @Json(name = "image")
    val image: String,
    @Json(name = "bloodGroup")
    val bloodGroup: String,
    @Json(name = "height")
    val height: Double,
    @Json(name = "weight")
    val weight: Double,
    @Json(name = "eyeColor")
    val eyeColor: String,
    @Json(name = "hair")
    val hair: Hair,
    @Json(name = "ip")
    val ip: String,
    @Json(name = "address")
    val address: Address,
    @Json(name = "macAddress")
    val macAddress: String,
    @Json(name = "university")
    val university: String,
    @Json(name = "bank")
    val bank: Bank,
    @Json(name = "company")
    val company: Company,
    @Json(name = "ein")
    val ein: String,
    @Json(name = "ssn")
    val ssn: String,
    @Json(name = "userAgent")
    val userAgent: String,
    @Json(name = "crypto")
    val crypto: Crypto,
    @Json(name = "role")
    val role: String
)

fun UserResponseModel.toGetAuthResponse() : GetUserAuthResponseDto {
    return GetUserAuthResponseDto(
        age = age,
        lastName = lastName,
        firstName = firstName,
        maidenName = maidenName
    )
}