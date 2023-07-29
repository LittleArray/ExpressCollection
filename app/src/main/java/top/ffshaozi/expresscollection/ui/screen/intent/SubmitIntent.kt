package top.ffshaozi.expresscollection.ui.screen.intent

import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import top.ffshaozi.expresscollection.config.Setting.USER_NAME
import top.ffshaozi.expresscollection.ui.screen.state.AppState.cr


class SubmitViewModel : ViewModel() {
    private var _contentText= MutableStateFlow(String());
    val contentText = _contentText
    private var _userName= MutableStateFlow(String());
    val userName = _userName
    private var bitmap: Bitmap? = null
    init {
        _userName.value = USER_NAME
    }
    fun sendContextIntent(str:String){
        _contentText.value = str
    }
    fun sendImgIntent(uri: Uri){
        bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri))
        _contentText.value = "选择图片路径:\n$uri\n图片宽:${bitmap!!.width}\n图片高:${bitmap!!.height}"

    }
    fun submit(){

    }
}
