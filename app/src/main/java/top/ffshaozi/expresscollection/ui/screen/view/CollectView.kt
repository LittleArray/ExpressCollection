package top.ffshaozi.expresscollection.ui.screen.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.expandVertically
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import top.ffshaozi.expresscollection.config.Setting.USER_NAME
import top.ffshaozi.expresscollection.ui.screen.intent.CollectIntent
import top.ffshaozi.expresscollection.ui.theme.ExpressCollectionTheme

@Composable
fun CollectView () {
    val testList:List<String> = listOf("8-3887","3栋二单元102","顺丰")
    val vm:CollectIntent = viewModel()
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
                text = "你好,$USER_NAME",
                color = MaterialTheme.colorScheme.primary,
                fontSize = 32.sp,
                textAlign = TextAlign.Left,
                modifier = Modifier
                    .padding(bottom = 15.dp)
            )
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
            ) {
                CradCollectView(
                    testList,
                    "【驿收发】取货码8-3887的顺丰包裹*3887已到南湖锦城3栋二单元102，请21:30前来取，详询17671048589",
                    "100001",
                    "200001",
                    vm
                )
                CradCollectView(
                    testList,
                    "【驿收发】取货码8-3887的顺丰包裹*3887已到南湖锦城3栋二单元102，请21:30前来取，详询17671048589",
                    "100001",
                    "200001",
                    vm
                )
                CradCollectView(
                    testList,
                    "【驿收发】取货码8-3887的顺丰包裹*3887已到南湖锦城3栋二单元102，请21:30前来取，详询17671048589",
                    "100001",
                    "200001",
                    vm
                )
            }
        }
    }
}

@Composable
fun CradCollectView(keywords: List<String>, context: String, uid: String, pid: String, viewModel: CollectIntent){
    var contentVisible by remember { mutableStateOf(true) }
    Column(){
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
                        keywords.forEach{
                            KeywordsCaed(it)
                        }
                    }
                    Text(
                        fontSize = 20.sp,
                        text = context
                    )
                    Text(
                        fontSize = 16.sp,
                        text = "UID:$uid PID:$pid"
                    )
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp),
                        onClick = {
                            viewModel.collect(uid,pid,true)
                            contentVisible=false
                        }
                    ) {
                        Text(text = "取出")
                    }
                    Button(
                        modifier = Modifier
                            .fillMaxWidth(),
                        onClick = {
                            viewModel.collect(uid,pid,false)
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
fun KeywordsCaed(context:String){
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