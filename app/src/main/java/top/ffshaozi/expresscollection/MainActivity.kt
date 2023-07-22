package top.ffshaozi.expresscollection

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.ViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import top.ffshaozi.expresscollection.config.AppState.cm
import top.ffshaozi.expresscollection.config.AppState.smsData
import top.ffshaozi.expresscollection.config.Route
import top.ffshaozi.expresscollection.ui.screen.view.MainNavView
import top.ffshaozi.expresscollection.ui.screen.view.UserLoginView
import top.ffshaozi.expresscollection.ui.screen.view.WelcomeView
import top.ffshaozi.expresscollection.ui.theme.ExpressCollectionTheme
import top.ffshaozi.expresscollection.utils.GetSMS.getSMS

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExpressCollectionTheme {
                val appNavController = rememberNavController()
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
    }
}

@Preview
@Composable
fun previewMain(){

}