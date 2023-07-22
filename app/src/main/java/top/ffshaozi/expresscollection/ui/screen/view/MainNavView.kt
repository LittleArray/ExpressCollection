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
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import top.ffshaozi.expresscollection.R
import top.ffshaozi.expresscollection.config.UIRoute
import top.ffshaozi.expresscollection.ui.theme.ExpressCollectionTheme
import kotlin.system.exitProcess


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

    val UiNavController = rememberNavController()
    UiNavController.addOnDestinationChangedListener{_,destination,_->
        UIRoute.apply {
            when (destination.route) {
                SUBMIT_EXP_PAGE-> {nowActiveIndex=0}
                COLLECT_EXP_PAGE -> {nowActiveIndex=1}
                SEETING_PAGE -> {nowActiveIndex=2}
            }
        }

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
                        nowActiveIndex = when(index){
                            0 ->{
                                UiNavController.mainNavTo(UIRoute.SUBMIT_EXP_PAGE)
                                index
                            }
                            1 ->{
                                UiNavController.mainNavTo(UIRoute.COLLECT_EXP_PAGE)
                                index
                            }
                            2 ->{
                                UiNavController.mainNavTo(UIRoute.SEETING_PAGE)
                                index
                            }else->{
                                -1
                            }
                        }
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
            NavHost(navController = UiNavController, startDestination = UIRoute.SUBMIT_EXP_PAGE){
                composable(UIRoute.SUBMIT_EXP_PAGE){
                    SubmitView()
                }
                composable(UIRoute.COLLECT_EXP_PAGE){
                    CollectView()
                }
                composable(UIRoute.SEETING_PAGE){
                    SettingsView()
                }
            }
        }
    }
}
fun NavHostController.mainNavTo(route:String) {
    this.navigate(route){
        popUpTo(this@mainNavTo.graph.findStartDestination().id)
        launchSingleTop=true
    }
}

@Preview(showBackground = true)
@Composable
fun previewMain(){
    ExpressCollectionTheme {
        MainNavView()
    }
}