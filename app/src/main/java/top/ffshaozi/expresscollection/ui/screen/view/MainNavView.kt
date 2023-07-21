package top.ffshaozi.expresscollection.ui.screen.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import top.ffshaozi.expresscollection.R
import top.ffshaozi.expresscollection.ui.theme.ExpressCollectionTheme



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainNavView(appNavController: NavHostController?=null) {
    val navList = listOf(
        Pair("提交", R.drawable.ic_action_add),
        Pair("代取", R.drawable.ic_action_outbox),
        Pair("设置", R.drawable.ic_action_settings))
    var nowActiveIndex by remember{
        mutableStateOf(0)
    }
    Scaffold (modifier = Modifier.fillMaxSize(),bottomBar = {
        NavigationBar {
            navList.forEachIndexed{index, pair ->
                NavigationBarItem(
                    selected = nowActiveIndex == index,
                    icon = {
                        Icon(
                            modifier = Modifier.size(30.dp),
                            painter = painterResource(id = pair.second),
                            contentDescription = pair.first,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    },
                    onClick = {
                        nowActiveIndex = index
                    },
                    label = {
                        Text(text = pair.first)
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedTextColor = MaterialTheme.colorScheme.primary
                    ))

            }
        }
    }){
        Box(modifier = Modifier.padding(it)){

        }
    }
}
@Preview(showBackground = true)
@Composable
fun previewMain(){
    ExpressCollectionTheme {
        MainNavView()
    }
}