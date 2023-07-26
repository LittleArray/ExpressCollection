package top.ffshaozi.expresscollection.ui.screen.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import top.ffshaozi.expresscollection.config.PreferencesKeys
import top.ffshaozi.expresscollection.config.Setting.SERVER_URL
import top.ffshaozi.expresscollection.config.Setting.USER_NAME
import top.ffshaozi.expresscollection.config.Setting.putValue
import top.ffshaozi.expresscollection.ui.theme.ExpressCollectionTheme

@Composable
fun SettingsView (){
    var name by remember {
        mutableStateOf(USER_NAME)
    }
    var url by remember {
        mutableStateOf(SERVER_URL)
    }
    Column (modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)
        .padding(15.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ){

        Column {
            Text(text = "设置",
                color = MaterialTheme.colorScheme.primary,
                fontSize = 32.sp,
                textAlign = TextAlign.Left,
                modifier = Modifier.padding(bottom = 15.dp)
            )
            OutlinedTextField(
                value = name,
                onValueChange = {
                    name = it
                },
                label = {
                    Text(
                        "用户名设置",
                        fontSize = 16.sp
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 15.dp)
            )
            OutlinedTextField(
                value = url,
                onValueChange = {
                    url = it
                },
                label = {
                    Text(
                        "服务器地址",
                        fontSize = 16.sp
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 15.dp)
            )
            Button(
                onClick = {
                    if (name != ""){
                        if (url !=""){
                            putValue(PreferencesKeys.USER_NAME,name)
                            putValue(PreferencesKeys.SERVER_URL,url)
                            USER_NAME=name
                            SERVER_URL=url
                        }
                    }
                          },
                modifier = Modifier.padding(bottom = 15.dp).fillMaxWidth()
            ){
                Text(text = "保存")
            }
        }
    }

}


@Preview(showBackground = true)
@Composable
fun previewSettings(){
    ExpressCollectionTheme {
        SettingsView()
    }
}