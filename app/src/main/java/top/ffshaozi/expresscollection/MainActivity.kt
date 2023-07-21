package top.ffshaozi.expresscollection

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import top.ffshaozi.expresscollection.config.Route
import top.ffshaozi.expresscollection.ui.theme.ExpressCollectionTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExpressCollectionTheme {
                val appNavController = rememberNavController()
                NavHost(navController = appNavController, startDestination = Route.USER_LOGIN_PAGE){
                    composable(Route.USER_LOGIN_PAGE){

                    }
                    composable(Route.MAIN_NAV){

                    }
                }


            }
        }
    }
}
@Preview
@Composable
fun previewMain(){

}