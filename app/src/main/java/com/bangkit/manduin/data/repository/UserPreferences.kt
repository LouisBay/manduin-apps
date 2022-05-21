package com.bangkit.manduin.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.bangkit.manduin.model.UserSessionModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserPreferences @Inject constructor(private val dataStore: DataStore<Preferences>) {
    fun getSession(): Flow<UserSessionModel> {
        return dataStore.data.map { pref ->
            UserSessionModel(
                pref[USER_UID] ?: "",
                pref[USER_NAME] ?: "",
                pref[USER_EMAIL] ?: "",
                pref[LOGIN_STATE] ?: false,
            )
        }
    }

    suspend fun saveSession(session: UserSessionModel) {
        dataStore.edit { pref ->
            pref[USER_UID] = session.uid
            pref[USER_NAME] = session.name
            pref[USER_EMAIL] = session.email
            pref[LOGIN_STATE] = session.isLogin
        }
    }

    suspend fun deleteSession() {
        dataStore.edit { pref ->
            pref[USER_UID] = ""
            pref[USER_NAME] = ""
            pref[USER_EMAIL] = ""
            pref[LOGIN_STATE] = false
        }
    }

    companion object {
        private val USER_UID = stringPreferencesKey("user_uid")
        private val USER_NAME = stringPreferencesKey("user_name")
        private val USER_EMAIL = stringPreferencesKey("user_email")
        private val LOGIN_STATE = booleanPreferencesKey("user_login_state")
    }
}