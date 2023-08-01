package top.ffshaozi.expresscollection

import android.annotation.SuppressLint
import android.content.ClipboardManager
import android.content.ContentResolver
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.DelicateCoroutinesApi
import top.ffshaozi.expresscollection.config.Route
import top.ffshaozi.expresscollection.config.Setting.setGlobalUserName
import top.ffshaozi.expresscollection.ui.screen.view.MainNavView
import top.ffshaozi.expresscollection.ui.screen.view.UserLoginView
import top.ffshaozi.expresscollection.ui.screen.view.WelcomeView
import top.ffshaozi.expresscollection.ui.theme.ExpressCollectionTheme
import top.ffshaozi.expresscollection.utils.GetSMS.getSMS
import kotlinx.coroutines.*
import top.ffshaozi.expresscollection.config.Setting.setGlobalServerUrl
import top.ffshaozi.expresscollection.utils.GetSMS

class MainActivity : ComponentActivity() {

    companion object {
        lateinit var cm: ClipboardManager
        lateinit var smsData: List<GetSMS.SMSData>
        lateinit var cr: ContentResolver
    }
    @SuppressLint("CoroutineCreationDuringComposition")
    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExpressCollectionTheme {
                val appNavController = rememberNavController()
                setGlobalUserName()
                setGlobalServerUrl()
                NavHost(navController = appNavController, startDestination = Route.WELCOME_PAGE){
                    composable(Route.USER_LOGIN_PAGE){
                        UserLoginView(appNavController)
                    }
                    composable(Route.WELCOME_PAGE){
                        WelcomeView(appNavController)
                    }
                    composable(Route.MAIN_NAV){
                        MainNavView(appNavController)
                    }
                }
            }
        }
        //获取剪切板管理器对象
        cm = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        //获取短信
        smsData = getSMS(this)
        //照片选择器
        cr = contentResolver

    }
}
@Preview
@Composable
fun previewMain(){

}