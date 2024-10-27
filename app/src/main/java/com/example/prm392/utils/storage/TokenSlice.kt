// TokenManager.kt
package com.example.prm392.utils

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore("user_prefs")

@Singleton
class TokenSlice @Inject constructor(
    context: Context
) {

    private val dataStore = context.dataStore
    private val tokenKey = stringPreferencesKey("auth_token")

    val token: Flow<String?> = dataStore.data.map { preferences ->
        preferences[tokenKey]
    }

    suspend fun saveToken(token: String) {
        dataStore.edit { preferences ->
            preferences[tokenKey] = token
        }
    }

    suspend fun clearToken() {
        dataStore.edit { preferences ->
            preferences.remove(tokenKey)
        }
    }

    suspend fun tokenExists(): Boolean {
        return token.map { it != null }.first()
    }
}
