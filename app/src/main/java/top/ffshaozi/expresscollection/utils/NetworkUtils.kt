package top.ffshaozi.expresscollection.utils

import com.google.gson.Gson
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import top.ffshaozi.expresscollection.config.Setting
import top.ffshaozi.expresscollection.config.Setting.USER_NAME
import top.ffshaozi.expresscollection.ui.screen.intent.*
import java.util.concurrent.CompletableFuture

object NetworkUtils {
    fun getData(result:(Data) -> Unit,state: (String) -> Unit) {
        Thread {
            val url = "${Setting.SERVER_URL}/getdata"
            val client = OkHttpClient()
            val request = Request.Builder()
                .url(url)
                .build()
            state("获取数据中")
            try {
                val response = client.newCall(request).execute()
                if (response.isSuccessful) {
                    val json = Gson().fromJson(response.body?.string(), Data::class.java)
                    result(json)
                    state("获取成功")
                }
            } catch (e: Exception) {
                result(
                    Data(
                        listOf(
                            CollectJson(
                                "", "", "", e.stackTraceToString(), listOf("出现错误,请重试")
                            )
                        )
                    )
                )
                state("获取失败")
            }
        }.start()
    }
    fun getMyData(result:(MyData) -> Unit, state: (String) -> Unit) {
        Thread {
            val url = "${Setting.SERVER_URL}/getMydata/$USER_NAME"
            val client = OkHttpClient()
            val request = Request.Builder()
                .url(url)
                .build()
            state("获取数据中")
            try {
                val response = client.newCall(request).execute()
                if (response.isSuccessful) {
                    val json = Gson().fromJson(response.body?.string(), MyData::class.java)
                    result(json)
                    state("获取成功")
                }
            } catch (e: Exception) {
                result(
                    MyData(
                        listOf(
                            MyCollectJson(
                                "", e.stackTraceToString(), "","", "",false,false
                            )
                        )
                    )
                )
                state("获取失败")
            }
        }.start()
    }
    fun postCollectState(pid: String,state:Boolean): String? {

        return CompletableFuture.supplyAsync{
            val url = "${Setting.SERVER_URL}/collect/pid=${pid}/state=${state}/username=$USER_NAME"
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
    fun postData(submitData: SubmitData,result: (String) -> Unit,state:(String) -> Unit) {

        Thread{
            val url = "${Setting.SERVER_URL}/postdata"
            val json = Gson().toJson(submitData)
            val requestBody = json?.let {
                //创建requestBody 以json的形式
                val contentType: MediaType = "application/json".toMediaType()
                json.toRequestBody(contentType)
            }
            val client = OkHttpClient()
            val request = Request.Builder()
                .url(url)
                .method("post",requestBody)
                .build()
            state("上传中")
            try {
                val response = client.newCall(request).execute()
                if (response.isSuccessful) {
                    response.body?.string()?.let {
                        result(it)
                        state("上传成功")
                    }
                }
            } catch (e: Exception) {
                result(e.stackTraceToString())
                state("上传失败")
            }

        }.start()
    }
}