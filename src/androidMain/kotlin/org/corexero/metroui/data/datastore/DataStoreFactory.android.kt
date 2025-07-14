package org.corexero.metroui.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

actual class MetroDataStoreFactory(private val context: Context) {

    companion object Companion {
        private var INSTANCE: DataStore<Preferences>? = null
        private fun getDataStore(context: Context): DataStore<Preferences> {
            if (INSTANCE == null) {
                synchronized(this) {
                    if (INSTANCE == null) {
                        INSTANCE = createDataStore(
                            producePath = { context.filesDir.resolve(dataStoreFileName).absolutePath }
                        )
                    }
                }
            }
            return INSTANCE!!
        }
    }

    actual fun createDataStore(): DataStore<Preferences> {
        return getDataStore(context)
    }
}