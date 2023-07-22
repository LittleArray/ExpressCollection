package top.ffshaozi.expresscollection.ui.screen.view

import android.content.ClipData
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.getSystemService
import com.setruth.yangdialog.YangDialog
import com.setruth.yangdialog.YangDialogDefaults
import top.ffshaozi.expresscollection.config.AppState.cm
import top.ffshaozi.expresscollection.config.AppState.smsData
import top.ffshaozi.expresscollection.ui.theme.ExpressCollectionTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubmitView (){
    var context by remember {
        mutableStateOf("")
    }
    var username by remember {
        mutableStateOf("")
    }
    var dialogShowMsg by remember {
        mutableStateOf(false)
    }
    YangDialog(
        title = "选择短信",
        isShow = dialogShowMsg,//通过isShow展示或者隐藏dialog
        onConfirm = {//确认选项的回调
            dialogShowMsg = false
        },
        //底部设置
        bottomConfig = YangDialogDefaults.bottomConfig(
            showCancel = false
        ),
        onDismissRequest = { //遮罩层点击的回调
            dialogShowMsg = false
        },
    ) {
        Box(
            modifier = Modifier
                .height(600.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Column {
                smsData.forEachIndexed { _, smsData ->
                    Card (
                        onClick = {
                            context = smsData.body.toString()
                            dialogShowMsg = false
                        },
                        modifier = Modifier
                            .padding(10.dp)
                    ){
                        Text(
                            text = smsData.address.toString() + "  " + smsData.date.toString() + "\n\n" + smsData.body.toString(),
                            modifier = Modifier
                                .padding(12.dp)
                        )
                    }
                }
            }
        }
    }
    Column (modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)
        .padding(15.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Column {
            Text(
                text = "你好,$username",
                color = MaterialTheme.colorScheme.primary,
                fontSize = 32.sp,
                textAlign = TextAlign.Left,
                modifier = Modifier
                    .padding(bottom = 15.dp)
            )

            OutlinedTextField(
                value = context,
                onValueChange = {
                    context = it
                },
                label = {
                    Text(
                        "输入快递内容",
                        fontSize = 16.sp
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .defaultMinSize(minWidth = Dp.Unspecified, minHeight = 300.dp)
            )
            Button(
                onClick = {/*TODO*/},
                modifier = Modifier
                    .padding(top = 15.dp)
                    .fillMaxWidth()
            ){
                Text(text = "提交")
            }
            Row (
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ){
                Button(
                onClick = {
                    context = getCM()
                          },
                modifier = Modifier
                    .padding(end = 7.dp)
                    .weight(1f)
                ){
                Text(text = "获取剪切板")
                }
                Button(
                    onClick = {
                        dialogShowMsg=true
                              },
                    modifier = Modifier
                        .padding(end = 7.dp)
                        .weight(1f)
                ){
                    Text(text = "获取短信")
                }
                Button(
                    onClick = {/*TODO*/},
                    modifier = Modifier
                        .weight(1f)
                ){
                    Text(text = "获取截图")
                } }
        }
    }
    Column (
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ){}
}
fun getCM():String{
    val clipData: ClipData? = cm.primaryClip
    if (clipData == null || clipData.itemCount <= 0) {
        return ""
    }
    val item = clipData.getItemAt(0)
    return item.text.toString()
}

@Preview(showBackground = true)
@Composable
fun previewSubmit(){
    ExpressCollectionTheme {
        SubmitView()
    }
}