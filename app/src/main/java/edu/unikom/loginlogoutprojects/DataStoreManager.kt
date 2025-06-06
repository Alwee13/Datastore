package edu.unikom.loginlogoutprojects

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("user_preferences")

data class DataStoreManager(private val context: Context) {

    private object PreferencesKeys {
        val NAMA = stringPreferencesKey("nama")
        val EMAIL = stringPreferencesKey("email")
        val NO_HP = stringPreferencesKey("no_hp")
    }

    suspend fun saveUserData(nama: String, email: String, noHp: String) {
        context.dataStore.edit { prefs ->
            prefs[PreferencesKeys.NAMA] = nama
            prefs[PreferencesKeys.EMAIL] = email
            prefs[PreferencesKeys.NO_HP] = noHp
        }
    }

    suspend fun clearUserData() {
        context.dataStore.edit { prefs ->
            prefs.clear()
        }
    }

    val userDataFlow: Flow<UserData?> = context.dataStore.data.map { prefs ->
        val nama = prefs[PreferencesKeys.NAMA]
        val email = prefs[PreferencesKeys.EMAIL]
        val noHp = prefs[PreferencesKeys.NO_HP]

        if (nama != null && email != null && noHp != null) {
            UserData(nama, email, noHp)
        } else {
            null
        }
    }
}
