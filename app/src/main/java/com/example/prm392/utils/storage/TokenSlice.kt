// TokenManager.kt
package com.example.prm392.utils

import android.content.Context
import android.util.Base64
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import org.json.JSONObject
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.log

private val Context.dataStore by preferencesDataStore("user_prefs")

@Singleton
class TokenSlice @Inject constructor(
    context: Context
) {

    private val dataStore = context.dataStore
    private val tokenKey = stringPreferencesKey("auth_token")
    private val userIdKey = stringPreferencesKey("user_id")
    private val roleKey = stringPreferencesKey("user_role")

    val token: Flow<String?> = dataStore.data.map { preferences ->
        preferences[tokenKey]
    }
    val userId: Flow<String?> = dataStore.data.map { preferences ->
        preferences[userIdKey]
    }
    val role: Flow<String?> = dataStore.data.map { preferences ->
        preferences[roleKey]
    }

    suspend fun saveToken(token: String) {
        val (userId,role) = decodeTokenPayload(token)
        dataStore.edit { preferences ->
            preferences[tokenKey] = token
            userId?.let {
                preferences[userIdKey] = it
            }
            role?.let { preferences[roleKey] = it }

        }
    }


    suspend fun clearToken() {
        dataStore.edit { preferences ->
            preferences.remove(tokenKey)
            preferences.remove(userIdKey)
            preferences.remove(roleKey)
        }
    }

    suspend fun tokenExists(): Boolean {
        return token.map { it != null }.first()
    }
    public fun decodeTokenPayload(token: String): Pair<String?, String?> {
        val tokenEx= "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxIiwiZW1haWwiOiJ1c2VyQGV4YW1wbGUuY29tIiwiaHR0cDovL3NjaGVtYXMubWljcm9zb2Z0LmNvbS93cy8yMDA4LzA2L2lkZW50aXR5L2NsYWltcy9yb2xlIjoiQ3VzdG9tZXIiLCJleHAiOjE3MzM2MzQ5MjN9.SUoxSquALNIdrw_ehSsXq-GNuiNP85nHzhE7h8MGBFo"
        return try {
            val parts = tokenEx.split(".")
            Log.d("Parts","${parts}" )
            if (parts.size == 3) {
                val payload = String(Base64.decode(parts[1], Base64.URL_SAFE))
                val jsonObject = JSONObject(payload)
                val userId = jsonObject.getString("sub")
                val role = jsonObject.getString("http://schemas.microsoft.com/ws/2008/06/identity/claims/role")
//                Log.d("Parts","${jsonObject}" )
//                Log.d("Parts", userId)
//                Log.d("Parts", "tẽ")
//                Log.d("Parts", role)
                Pair(userId,role)
            } else {
                Pair(null, null)
            }
        } catch (e: Exception) {
            Pair(null, null)
        }
    }
}
