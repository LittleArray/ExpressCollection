package top.ffshaozi.expresscollection.ui.screen.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import top.ffshaozi.expresscollection.config.Route

@Composable
fun WelcomeView(appNavController: NavHostController?=null) {
    var username = remember { mutableStateOf("") }
    var contentVisible1 by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        delay(200)
        contentVisible1 = true
        delay(2500)
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
                text = "欢迎回来\n$username",
                color = MaterialTheme.colorScheme.primary,
                fontSize = 36.sp,
                lineHeight = 48.sp,
                textAlign = TextAlign.Center,
                maxLines = 2
            )
        }
    }

}