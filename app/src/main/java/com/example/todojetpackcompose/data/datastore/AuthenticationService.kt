package com.example.todojetpackcompose.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

class AuthenticationService(
    private val context: Context,
) {
    companion object {
        val KEY_TOKEN = stringPreferencesKey("key_token")
        val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = "app_datastore")
    }

    fun isAuthenticated(): Flow<Boolean> {
        return context.datastore.data.map {
            it.contains(KEY_TOKEN)
        }
    }

    suspend fun store(token: String) {
        context.datastore.edit {
            it[KEY_TOKEN] = token
        }
    }

    suspend fun getToken(): String {
        return context.datastore.data
            .map { it[KEY_TOKEN] }
            .firstOrNull()
            ?: throw IllegalArgumentException("no token stored")
    }

    suspend fun onLogout() {
        context.datastore.edit {
            it.remove(KEY_TOKEN)
        }
    }
}