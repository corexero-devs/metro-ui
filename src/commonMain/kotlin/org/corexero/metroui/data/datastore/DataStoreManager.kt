package org.corexero.metroui.data.datastore

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

sealed class DataStoreKey<T>(val key: Preferences.Key<T>, val defaultValue: T) {
    data object ShowInAppReview : DataStoreKey<Boolean>(
        key = booleanPreferencesKey("SHOW_IN_APP_REVIEW"),
        defaultValue = true
    )

    data object RecentSearches : DataStoreKey<String>(
        key = stringPreferencesKey("RECENT_SEARCHES"),
        defaultValue = ""
    )
}

class DataStoreManager(metroDataStoreFactory: MetroDataStoreFactory) {
    private val dataStore = metroDataStoreFactory.createDataStore()

    fun <T> getFlow(dataStoreKey: DataStoreKey<T>): Flow<T> =
        dataStore.data.map { preferences ->
            val result = preferences[dataStoreKey.key] ?: dataStoreKey.defaultValue
            result
        }

    suspend fun <T> getFirst(dataStoreKey: DataStoreKey<T>): T {
        return dataStore.data.first()[dataStoreKey.key] ?: dataStoreKey.defaultValue
    }

    suspend fun <T> put(dataStoreKey: DataStoreKey<T>, value: T) {
        dataStore.edit {
            it[dataStoreKey.key] = value
        }
    }
}



