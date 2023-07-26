package top.ffshaozi.expresscollection.ui.screen.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import top.ffshaozi.expresscollection.config.Route
import top.ffshaozi.expresscollection.config.Setting.USER_NAME
import top.ffshaozi.expresscollection.ui.theme.ExpressCollectionTheme
import kotlin.system.exitProcess

@Composable
fun WelcomeView(appNavController: NavHostController?=null) {
    var contentVisible1 by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        for (item: Int in 1..10) {
            if (USER_NAME != ""){
                if (USER_NAME == "nul"){
                    appNavController?.navigate(Route.USER_LOGIN_PAGE)
                    return@LaunchedEffect
                }
                contentVisible1 = true
            }
            delay(100)
        }

        delay(100)
        appNavController?.navigate(Route.MAIN_NAV)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimatedVisibility(
            visible = contentVisible1,
            enter =
            expandVertically(expandFrom = Alignment.Bottom)
        ) {
            Text(
                text = "欢迎回来\n$USER_NAME",
                color = MaterialTheme.colorScheme.primary,
                fontSize = 36.sp,
                lineHeight = 48.sp,
                textAlign = TextAlign.Center,
                maxLines = 2,
                modifier = Modifier.padding(bottom = 15.dp)
            )
        }
        Button(onClick = { exitProcess(0)}){
            Text(text = "     退出     ")
        }
    }
}
@Preview(showBackground = true)
@Composable
fun previewWelcome(){
    ExpressCollectionTheme {
        WelcomeView()
    }
}