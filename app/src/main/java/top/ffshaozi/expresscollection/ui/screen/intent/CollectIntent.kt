package top.ffshaozi.expresscollection.ui.screen.intent

import androidx.lifecycle.ViewModel
import okhttp3.*
import java.io.IOException
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import top.ffshaozi.expresscollection.config.Setting.SERVER_URL
import java.util.concurrent.CompletableFuture
import java.util.concurrent.LinkedBlockingQueue


data class CollectJson(
    val pid: String,
    val userName:String,
    val contentType:String,
    val content:String,
    val keywords:List<String>
)
data class Data(
    val data:List<CollectJson>
)


class CollectIntent : ViewModel (){

    fun collect(pid:String,state:Boolean){
        postData(pid,state)
    }

    fun getData(): Data? {

       return CompletableFuture.supplyAsync{
            val url = "$SERVER_URL/getdata"
            var temp:Data?=null
            val client = OkHttpClient()
            val request = Request.Builder()
                .url(url)
                .build()
            try {
                val response = client.newCall(request).execute()
                if (response.isSuccessful) {
                    val result = response.body?.string()
                    temp = Gson().fromJson(result, Data::class.java)

                }
            } catch (e: Exception) {
               temp = Data(
                   listOf(
                       CollectJson("", "", "", e.stackTraceToString(), listOf()
                       )
                   )
               )
            }
            temp
        }.get()

    }
    fun postData(pid: String,state:Boolean): String? {

        return CompletableFuture.supplyAsync{
            val url = "$SERVER_URL/post?pid=${pid}?state=${state}"
            var temp:String ?= null
            val client = OkHttpClient()
            val request = Request.Builder()
                .url(url)
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
}