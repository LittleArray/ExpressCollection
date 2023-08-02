package top.ffshaozi.expresscollection.ui.screen.intent

import androidx.lifecycle.ViewModel
import top.ffshaozi.expresscollection.utils.NetworkUtils.postCollectState


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
        postCollectState(pid,state)
    }



}