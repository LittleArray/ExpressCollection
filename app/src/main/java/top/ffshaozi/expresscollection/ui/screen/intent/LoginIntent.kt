package top.ffshaozi.expresscollection.ui.screen.intent

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import top.ffshaozi.expresscollection.config.Setting.SERVER_URL
import top.ffshaozi.expresscollection.config.Setting.USER_NAME

class LoginViewModel : ViewModel() {
    private var _serverUrl= MutableStateFlow(String());
    val serverUrl = _serverUrl
    private var _userName= MutableStateFlow(String());
    val userName = _userName

    fun sendIntent(userName:String?=null,serverUrl:String?=null){
        if (serverUrl != null) {
            _serverUrl.value = serverUrl
            SERVER_URL = serverUrl
        }
        if (userName != null) {
            _userName.value = userName
            USER_NAME = userName
        }
    }
}
