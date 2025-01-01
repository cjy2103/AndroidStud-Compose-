package com.example.btexample.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import javax.inject.Inject

// Context 확장 프로퍼티로 Preferences DataStore 선언
val Context.dataStore: DataStore<androidx.datastore.preferences.core.Preferences> by preferencesDataStore(name = "settings")

class SpeedDataStore @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val SPEED_KEY = intPreferencesKey("speed")

    suspend fun saveSpeed(speed: Int) {
        context.dataStore.edit { preferences ->
            preferences[SPEED_KEY] = speed
        }
    }

    suspend fun getSpeed(): Int? {
        val preferences = context.dataStore.data.first()
        return preferences[SPEED_KEY]
    }
}