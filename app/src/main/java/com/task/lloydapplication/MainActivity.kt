package com.task.lloydapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.task.lloydapplication.screens.CategoryScreen
import com.task.lloydapplication.screens.DetailScreen
import com.task.lloydapplication.ui.theme.LloydApplicationTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LloydApplicationTheme {
                Scaffold(topBar = {
                    TopAppBar(title = {
                        Text(text = "Lloyd App")
                    } )
                }) {
                    Box(modifier = Modifier.padding(it)){
                        App()
                    }
                }

            }
        }
    }
}

@Composable
fun App(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "category"){
        composable(route = "category"){
            CategoryScreen {
                navController.navigate("tweetDetail/${it}")
            }
        }
        composable(route = "tweetDetail/{category}",
            arguments = listOf(navArgument("category"){
                type = NavType.StringType
            }
            )
        ){
            DetailScreen()
        }
    }
}