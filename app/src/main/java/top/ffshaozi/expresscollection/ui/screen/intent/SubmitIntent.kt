package top.ffshaozi.expresscollection.ui.screen.intent

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import top.ffshaozi.expresscollection.MainActivity.Companion.cr
import top.ffshaozi.expresscollection.config.Setting
import top.ffshaozi.expresscollection.config.Setting.USER_NAME
import java.io.ByteArrayOutputStream
import java.util.*
import java.util.concurrent.CompletableFuture
data class SubmitData(
    val userName:String,
    val contentType:String,
    val content:String,
)

class SubmitViewModel : ViewModel() {
    private var _contentText= MutableStateFlow(String());
    val contentText = _contentText
    private var _userName= MutableStateFlow(String());
    val userName = _userName
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
    fun postData(submitData:SubmitData): String? {

        return CompletableFuture.supplyAsync{
            val url = "${Setting.SERVER_URL}/postdata"
            val requestBody = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", "json",
                    Gson().toJson(submitData).toRequestBody("application/octet-stream".toMediaTypeOrNull())
                )
                .build()
            var temp:String ?= null
            val client = OkHttpClient()
            val request = Request.Builder()
                .url(url)
                .method("post",requestBody)
                .build()
            try {
                val response = client.newCall(request).execute()
                if (response.isSuccessful) {
                    temp = response.body?.string()
                }
            } catch (e: Exception) {
                temp =e.stackTraceToString()
            }
            temp
        }.get()
    }
    fun submit(){
        if (base64!=null){
            postData(SubmitData(USER_NAME,"base64", base64.toString()))
            base64=null
        }else{
            postData(SubmitData(USER_NAME,"text", contentText.value))
        }
    }

}
