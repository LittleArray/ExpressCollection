package top.ffshaozi.expresscollection.ui.screen.intent

import android.net.Uri
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import top.ffshaozi.expresscollection.config.Setting.USER_NAME


class SubmitViewModel : ViewModel() {
    private var _contentText= MutableStateFlow(String());
    val contentText = _contentText
    private var _userName= MutableStateFlow(String());
    val userName = _userName
    init {
        _userName.value = USER_NAME
    }
    fun sendContextIntent(str:String){
        _contentText.value = str
    }
    fun sendImgIntent(uri: Uri){
        _contentText.value = "选择图片路径:$uri"
    }
    fun submit(){

    }
}
