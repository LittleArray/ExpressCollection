package top.ffshaozi.expresscollection.ui.screen.intent

import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import kotlinx.coroutines.flow.MutableStateFlow
import top.ffshaozi.expresscollection.config.PreferencesKeys
import top.ffshaozi.expresscollection.config.Route
import top.ffshaozi.expresscollection.config.Setting
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
    fun submit(appNav: NavHostController):String{
        var err = ""
        if (SERVER_URL!=""){
            if (USER_NAME!=""){
                Setting.putValue(PreferencesKeys.USER_NAME, USER_NAME)
                Setting.putValue(PreferencesKeys.SERVER_URL, SERVER_URL)
                appNav.navigate(Route.WELCOME_PAGE)
            }else{
                return "请输入用户名\n"
            }
        }else{
            return "请输入服务器地址\n"
        }
        return ""
    }
}
