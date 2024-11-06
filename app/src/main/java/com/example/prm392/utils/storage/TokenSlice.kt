// TokenManager.kt
package com.example.prm392.utils

import android.content.Context
import android.util.Base64
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import org.json.JSONObject
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore("user_prefs")

@Singleton
class TokenSlice @Inject constructor(
    context: Context
) {

    private val dataStore = context.dataStore
    private val tokenKey = stringPreferencesKey("auth_token")
    private val userIdKey = stringPreferencesKey("user_id")

    val token: Flow<String?> = dataStore.data.map { preferences ->
        preferences[tokenKey]
    }
    val userId: Flow<String?> = dataStore.data.map { preferences ->
        preferences[userIdKey]
    }

    suspend fun saveToken(token: String) {
        val userId = decodeTokenPayload(token)
        dataStore.edit { preferences ->
            preferences[tokenKey] = token
            userId?.let {
                preferences[userIdKey] = it
            }
        }
    }


    suspend fun clearToken() {
        dataStore.edit { preferences ->
            preferences.remove(tokenKey)
            preferences.remove(userIdKey)
        }
    }

    suspend fun tokenExists(): Boolean {
        return token.map { it != null }.first()
    }
    private fun decodeTokenPayload(token: String): String? {
        return try {
            val parts = token.split(".")
            if (parts.size == 3) {
                val payload = String(Base64.decode(parts[1], Base64.URL_SAFE))
                val jsonObject = JSONObject(payload)
                jsonObject.getString("nameid")
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }
}
