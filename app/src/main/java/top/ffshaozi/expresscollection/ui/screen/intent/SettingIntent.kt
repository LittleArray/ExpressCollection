package top.ffshaozi.expresscollection.ui.screen.intent

import androidx.lifecycle.ViewModel
import top.ffshaozi.expresscollection.config.PreferencesKeys
import top.ffshaozi.expresscollection.config.Setting
import top.ffshaozi.expresscollection.config.Setting.SERVER_URL
import top.ffshaozi.expresscollection.config.Setting.USER_NAME

class SettingViewModel : ViewModel() {
    fun save(userName:String,serverUrl:String){
        Setting.putValue(PreferencesKeys.USER_NAME, userName)
        Setting.putValue(PreferencesKeys.SERVER_URL, serverUrl)
        USER_NAME = userName
        SERVER_URL = serverUrl
    }

}