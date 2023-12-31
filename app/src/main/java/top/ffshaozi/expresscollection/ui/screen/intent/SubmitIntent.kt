package top.ffshaozi.expresscollection.ui.screen.intent

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import top.ffshaozi.expresscollection.MainActivity.Companion.cr
import top.ffshaozi.expresscollection.config.Setting.USER_NAME
import top.ffshaozi.expresscollection.utils.NetworkUtils.postData
import java.io.ByteArrayOutputStream
import java.util.*

data class SubmitData(
    val userName:String,
    val contentType:String,
    val content:String,
)
data class MyData(
    val data:List<MyCollectJson>
)
data class MyCollectJson(
    val contentType:String,
    val content:String,
    val pid: String,
    val collectTime:String,
    val collectUserName:String,
    val isCollect:Boolean,
    val collectState:Boolean
)
class SubmitViewModel : ViewModel() {
    private var _contentText= MutableStateFlow(String())
    val contentText = _contentText
    private var _userName= MutableStateFlow(String())
    val userName = _userName
    private var _subState= MutableStateFlow(String())
    val subState = _subState
    private var bitmap: Bitmap? = null
    private var base64: String? = null
    init {
        _userName.value = USER_NAME
    }
    fun sendContextIntent(str:String){
        _contentText.value = str
    }
    @OptIn(DelicateCoroutinesApi::class)
    fun sendImgIntent(uri: Uri){
        bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri))
        GlobalScope.launch {
            base64 = byte2Base64(bitmap2Byte(bitmap))
        }
        _contentText.value = "选择图片路径:\n$uri\n图片宽:${bitmap!!.width}\n图片高:${bitmap!!.height}"

    }
    private fun bitmap2Byte(bitmap: Bitmap?): ByteArray? {
        if (null == bitmap) throw NullPointerException()
        // if (null == bitmap) return null;
        val outputStream = ByteArrayOutputStream()
        //把bitmap100%高质量压缩 到 output对象里
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        return outputStream.toByteArray()
    }
    private fun byte2Base64(imageByte: ByteArray?): String? {
        return if (null == imageByte) null else Base64.getEncoder().encodeToString(imageByte)
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun submit(){
        if (contentText.value!="") {
            postData(SubmitData(USER_NAME, "text", contentText.value), {}, { _subState.value = it })
        }else{
            GlobalScope.launch {
                _subState.value = "上传中"
                delay(1000)
                _subState.value = "上传失败"
            }

        }
    }

}
