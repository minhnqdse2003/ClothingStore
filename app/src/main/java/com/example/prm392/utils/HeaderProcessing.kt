// HeaderManager.kt
package com.example.prm392.utils

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HeaderProcessing @Inject constructor(
    private val tokenSlice: TokenSlice
) {
    fun createDynamicHeaders(
        isTokenIncluded: Boolean = false,
        userAgent: String = "PRM392",
        contentType: String = "application/json",
        customHeaders: Map<String, String> = emptyMap()
    ): Map<String, String> {
        val headers = mutableMapOf(
            "Accept" to "application/json",
            "User-Agent" to userAgent,
            "Content-Type" to contentType
        )

        if (isTokenIncluded) {
            val token = runBlocking { tokenSlice.token.first() }
            if (token != null) {
                headers["Authorization"] = "Bearer $token"
            }
        }

        headers.putAll(customHeaders)
        return headers
    }
}
