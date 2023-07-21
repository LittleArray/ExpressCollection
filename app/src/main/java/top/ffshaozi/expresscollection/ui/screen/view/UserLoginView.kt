package top.ffshaozi.expresscollection.ui.screen.view


import android.widget.EditText
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.magnifier
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import top.ffshaozi.expresscollection.config.Route
import top.ffshaozi.expresscollection.ui.theme.ExpressCollectionTheme


@Composable
fun UserLoginView(appNavController: NavHostController?=null) {
    var username = remember {
        mutableStateOf("")
    }
    var serverUrl = remember {
        mutableStateOf("")
    }
    var contentVisible1 by remember{ mutableStateOf(false) }
    var contentVisible2 by remember{ mutableStateOf(false) }
    LaunchedEffect(Unit){
        delay(500)
        contentVisible1 = true
        delay(350)
        contentVisible2 = true
    }
    Column(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        AnimatedVisibility(
            visible = contentVisible1,
            enter =
            expandVertically(expandFrom = Alignment.Bottom)
                    + fadeIn(initialAlpha = 0.1f)
        ){
        Text(text = "欢迎使用\n快递代收工具",
            color = MaterialTheme.colorScheme.primary,
            fontSize = 42.sp,
            modifier = Modifier
                .padding(top = 95.dp)
                .padding(bottom = 175.dp),
            lineHeight = 48.sp,
            textAlign = TextAlign.Center
        )
        }
        AnimatedVisibility(
            visible = contentVisible2,
            enter = fadeIn(initialAlpha = 0.1f)
        ) {
            Column(modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TextField(
                        value = username.value,
                        onValueChange = {
                            username.value = it
                        },
                        label = {
                            Text(
                                "用户名",
                                fontSize = 18.sp
                            )
                        }
                    )


                    TextField(
                        value = serverUrl.value,
                        onValueChange = {
                            serverUrl.value = it
                        },
                        label = {
                            Text(
                                "服务器地址",
                                fontSize = 18.sp
                            )
                        },
                        modifier = Modifier
                            .padding(top = 10.dp)
                    )


                    Button(
                        onClick = {/*TODO*/ },
                        modifier = Modifier
                            .width(250.dp)
                            .padding(top = 15.dp)
                    ) {
                        Text(text = "确定")
                    }
                }
        }

    }
}
@Preview(showBackground = true)
@Composable
fun preview(){
    ExpressCollectionTheme {
        UserLoginView()
    }
}