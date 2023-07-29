package top.ffshaozi.expresscollection.ui.screen.view

import android.annotation.SuppressLint
import android.content.ClipData
import android.icu.text.SimpleDateFormat
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.setruth.yangdialog.YangDialog
import com.setruth.yangdialog.YangDialogDefaults
import top.ffshaozi.expresscollection.ui.screen.intent.SubmitViewModel
import top.ffshaozi.expresscollection.ui.screen.state.AppState.cm
import top.ffshaozi.expresscollection.ui.screen.state.AppState.smsData
import top.ffshaozi.expresscollection.ui.theme.ExpressCollectionTheme
import java.util.*

@SuppressLint("SimpleDateFormat")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubmitView (){
    val vm:SubmitViewModel= viewModel()
    val contentVM by vm.contentText.collectAsState()
    val username by vm.userName.collectAsState()
    var dialogShowMsg by remember { mutableStateOf(false) }
    var dialogShowExp by remember { mutableStateOf(false) }
    var dialogShowImg by remember { mutableStateOf(false) }
    //图片选择器
    var imageUri: Uri? by remember {
        mutableStateOf(null)
    }
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.OpenDocument()) {
            it?.let { uri ->
                dialogShowImg = true
                imageUri = uri
            }
        }
    //图片显示
    YangDialog(
        title = "选择的图片",
        isShow = dialogShowImg,//通过isShow展示或者隐藏dialog
        onConfirm = {//确认选项的回调
            imageUri?.let { vm.sendImgIntent(it) }
            dialogShowImg = false
        },
        //底部设置
        bottomConfig = YangDialogDefaults.bottomConfig(
            showCancel = false,
        ),
        onDismissRequest = { //遮罩层点击的回调
            dialogShowImg = false
        },
    ) {
        AsyncImage(
            model = imageUri,
            contentDescription = null,
            contentScale = ContentScale.FillWidth
        )
    }
    //我的快递
    YangDialog(
        title = "我的快递",
        isShow = dialogShowExp,//通过isShow展示或者隐藏dialog
        onConfirm = {//确认选项的回调
            dialogShowExp = false
        },
        //底部设置
        bottomConfig = YangDialogDefaults.bottomConfig(
            showCancel = false,
            showConfirm = false
        ),
        onDismissRequest = { //遮罩层点击的回调
            dialogShowExp = false
        },
    ) {
        Box(
            modifier = Modifier
                .height(600.dp)
                .verticalScroll(rememberScrollState())
        ) {

        }
    }
    //选择短信Dialog
    YangDialog(
        title = "选择短信",
        isShow = dialogShowMsg,//通过isShow展示或者隐藏dialog
        onConfirm = {//确认选项的回调
            dialogShowMsg = false
        },
        //底部设置
        bottomConfig = YangDialogDefaults.bottomConfig(
            showCancel = false,
            showConfirm = false
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
                    if (smsData.body?.indexOf("快递")!=-1) {
                        val dateFormat = SimpleDateFormat(
                            "yyyy-MM-dd hh:mm:ss"
                        )
                        val d = smsData.date?.let { Date(it) }
                        val strDate: String = dateFormat.format(d)

                        Card(
                            onClick = {
                                vm.sendContextIntent(smsData.body.toString())
                                dialogShowMsg = false
                            },
                            modifier = Modifier
                                .padding(10.dp)
                        ) {
                            Text(
                                text = smsData.address.toString() + "\n" + strDate + "\n\n" + smsData.body.toString(),
                                modifier = Modifier
                                    .padding(12.dp)
                            )
                        }
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
                value = contentVM,
                onValueChange = {
                    vm.sendContextIntent(it)
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
                onClick = {vm.submit()},
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
                onClick = { vm.sendContextIntent(getCM()) },
                modifier = Modifier
                    .padding(end = 7.dp)
                    .weight(1f)
                ){
                Text(text = "获取剪切板")
                }
                Button(
                    onClick = { dialogShowMsg=true },
                    modifier = Modifier
                        .padding(end = 7.dp)
                        .weight(1f)
                ){
                    Text(text = "获取短信")
                }
                Button(
                    onClick = { launcher.launch(arrayOf("image/*")) },
                    modifier = Modifier
                        .weight(1f)
                ){
                    Text(text = "获取截图")
                }
            }
            Button(
                onClick = {dialogShowExp = true},
                modifier = Modifier
                    .fillMaxWidth()
            ){
                Text(text = "我的快递")
            }

        }
    }

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