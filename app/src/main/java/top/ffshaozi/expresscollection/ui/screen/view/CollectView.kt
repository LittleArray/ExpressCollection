package top.ffshaozi.expresscollection.ui.screen.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.setruth.yangdialog.YangDialog
import com.setruth.yangdialog.YangDialogDefaults
import kotlinx.coroutines.delay
import top.ffshaozi.expresscollection.config.Setting.USER_NAME
import top.ffshaozi.expresscollection.ui.screen.intent.CollectIntent
import top.ffshaozi.expresscollection.ui.screen.intent.CollectJson
import top.ffshaozi.expresscollection.ui.theme.ExpressCollectionTheme
import top.ffshaozi.expresscollection.utils.NetworkUtils.getData

@Composable
fun CollectView () {
    val vm:CollectIntent = viewModel()
    val stateList = remember { mutableStateListOf<CollectJson>()}
    var dialogShowSubState by remember { mutableStateOf(false) }
    var dialogShowText by remember { mutableStateOf("") }
    LaunchedEffect (Unit){
        getData(result = { data ->
            data.data.forEach{
                stateList.add(it)
            }
        }, state = {
            dialogShowText = it
        })
    }
    if (dialogShowText == "获取数据中"){
        dialogShowSubState = true
    }
    if (dialogShowText == "获取成功"){
        LaunchedEffect(Unit) {
            delay(100)
            dialogShowSubState = false
        }
    }
    if (dialogShowText == "获取失败"){
        LaunchedEffect(Unit){
            delay(1000)
            dialogShowSubState = false
        }
    }
    //加载显示
    YangDialog(
        title = "请稍后...",
        isShow = dialogShowSubState,//通过isShow展示或者隐藏dialog
        onConfirm = {//确认选项的回调
        },
        //底部设置
        bottomConfig = YangDialogDefaults.bottomConfig(
            showCancel = false,
            showConfirm = false
        ),
        onDismissRequest = { //遮罩层点击的回调
        },
    ) {
        Row {
            CircularProgressIndicator(modifier = Modifier.padding(10.dp))
            Text(text = dialogShowText, modifier = Modifier.padding(10.dp).padding(top=10.dp))
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(15.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column {
            Text(
                text = "你好,${USER_NAME}",
                color = MaterialTheme.colorScheme.primary,
                fontSize = 32.sp,
                textAlign = TextAlign.Left,
                modifier = Modifier
                    .padding(bottom = 15.dp)
            )
            LazyColumn{
                itemsIndexed(stateList) { index, item ->
                    CardCollectView(item,vm,stateList,index)
                }
            }

        }
    }
}

@Composable
fun CardCollectView(
    collectJson: CollectJson,
    viewModel: CollectIntent,
    stateList: SnapshotStateList<CollectJson>,
    index: Int
){
    var contentVisible by remember { mutableStateOf(true) }
    Column{
        AnimatedVisibility(
            visible = contentVisible,
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(15.dp)
                ) {

                    Row(
                        modifier = Modifier
                            .padding(bottom = 20.dp)
                            .horizontalScroll(rememberScrollState())
                    ) {
                        Text(
                            text = "关键字",
                            fontSize = 28.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(end = 10.dp, top = 5.dp)
                        )
                        collectJson.keywords.forEach{
                            KeywordsCard(it)
                        }
                    }
                    Text(
                        fontSize = 20.sp,
                        text = collectJson.content
                    )
                    Text(
                        fontSize = 16.sp,
                        text = "PID:${collectJson.pid} 提交者:${collectJson.userName}"
                    )
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp),
                        onClick = {
                            viewModel.collect(collectJson.pid,true)
                            contentVisible=false
                        }
                    ) {
                        Text(text = "取出")
                    }
                    Button(
                        modifier = Modifier
                            .fillMaxWidth(),
                        onClick = {
                            viewModel.collect(collectJson.pid,false)
                            contentVisible=false
                        }
                    ) {
                        Text(text = "出错")
                    }
                }

            }
        }
    }

}

@Composable
fun KeywordsCard(context:String){
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
        modifier = Modifier
            .padding(end = 10.dp)
    ) {
        Text(fontSize = 24.sp,text = context, modifier = Modifier.padding(10.dp))
    }
}
@Preview(showBackground = true)
@Composable
fun previewCollect(){
    ExpressCollectionTheme{
        CollectView()
    }
}