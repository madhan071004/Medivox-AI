package com.medai.app.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "medai_prefs")

@Singleton
class PrefsManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val API_KEY   = stringPreferencesKey(Constants.PREF_API_KEY)
    private val DARK_MODE = booleanPreferencesKey(Constants.PREF_DARK_MODE)
    private val LANGUAGE  = stringPreferencesKey(Constants.PREF_LANGUAGE)
    private val USER_NAME = stringPreferencesKey(Constants.PREF_USER_NAME)
    private val USER_EMAIL= stringPreferencesKey(Constants.PREF_USER_EMAIL)

    val apiKey: Flow<String> = context.dataStore.data.map { it[API_KEY] ?: "" }
    val darkMode: Flow<Boolean> = context.dataStore.data.map { it[DARK_MODE] ?: true }
    val language: Flow<String> = context.dataStore.data.map { it[LANGUAGE] ?: "en" }
    val userName: Flow<String> = context.dataStore.data.map { it[USER_NAME] ?: "" }
    val userEmail: Flow<String> = context.dataStore.data.map { it[USER_EMAIL] ?: "" }

    suspend fun saveApiKey(key: String) = context.dataStore.edit { it[API_KEY] = key }
    suspend fun saveDarkMode(dark: Boolean) = context.dataStore.edit { it[DARK_MODE] = dark }
    suspend fun saveLanguage(lang: String) = context.dataStore.edit { it[LANGUAGE] = lang }
    suspend fun saveUser(name: String, email: String) = context.dataStore.edit {
        it[USER_NAME]  = name
        it[USER_EMAIL] = email
    }
    suspend fun clear() = context.dataStore.edit { it.clear() }
}
