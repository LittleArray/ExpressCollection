package top.ffshaozi.expresscollection

import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import top.ffshaozi.expresscollection.ui.screen.state.AppState.cm
import top.ffshaozi.expresscollection.ui.screen.state.AppState.pickMedia
import top.ffshaozi.expresscollection.ui.screen.state.AppState.smsData
import top.ffshaozi.expresscollection.config.Route
import top.ffshaozi.expresscollection.config.Setting.USER_NAME
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
                var strRoute = if(USER_NAME==""){
                    Route.USER_LOGIN_PAGE
                } else {
                    Route.WELCOME_PAGE
                }
                NavHost(navController = appNavController, startDestination = strRoute){
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
        // Registers a photo picker activity launcher in single-select mode.
        pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            // Callback is invoked after the user selects a media item or closes the
            // photo picker.
            if (uri != null) {
                Log.e("PhotoPicker", "Selected URI: $uri")
            } else {
                Log.e("PhotoPicker", "No media selected")
            }
        }
    }
}

@Preview
@Composable
fun previewMain(){

}