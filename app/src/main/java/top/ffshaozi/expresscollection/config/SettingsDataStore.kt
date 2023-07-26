package top.ffshaozi.expresscollection.config

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import top.ffshaozi.expresscollection.App

object SettingsDataStore{
    // 创建DataStore
    private val App.dataStore: DataStore<Preferences> by preferencesDataStore(
        name = "settings"
    )
    // 对外开放的DataStore变量
    val dataStore = App.instance.dataStore
}
object PreferencesKeys{
    val USER_NAME = stringPreferencesKey("USER_NAME")
    val SERVER_URL = stringPreferencesKey("SERVER_URL")
}