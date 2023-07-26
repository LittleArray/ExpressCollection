package top.ffshaozi.expresscollection.config

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import top.ffshaozi.expresscollection.config.SettingsDataStore.dataStore

@OptIn(DelicateCoroutinesApi::class)
object Setting {

    var USER_NAME:String=""
    var SERVER_URL:String=""
    private var returnResult:String=""
    fun putValue(key: Preferences.Key<String>, value: String) {
        GlobalScope.launch {
            dataStore.edit {
                it[key] = value
            }
        }
    }
    fun setGlobalUserName() {
        GlobalScope.launch {
            val preferencesFlow: Flow<String> =dataStore.data.map {
                it[PreferencesKeys.USER_NAME] ?: "nul"
            }
            USER_NAME = preferencesFlow.first()
        }
    }fun setGlobalServerUrl() {
        GlobalScope.launch {
            val preferencesFlow: Flow<String> =dataStore.data.map {
                it[PreferencesKeys.SERVER_URL] ?: "nul"
            }
            SERVER_URL = preferencesFlow.first()
        }
    }
}
