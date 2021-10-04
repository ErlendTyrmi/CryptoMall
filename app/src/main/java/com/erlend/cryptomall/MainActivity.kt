package com.erlend.cryptomall

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.erlend.cryptomall.ui.theme.CryptoMallTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CryptoMallTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Navigation()
                }
            }
        }
    }
}

@Composable
fun Navigation(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "splash"){
        composable(route = "splash"){
            Splash(navnController = navController)
        }
        composable(route = "start"){
            Box(modifier = Modifier.fillMaxSize()){
                Text(text = "Start here!", modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}

@Composable
fun Splash(navnController: NavController) {
    LaunchedEffect(key1 = true){
        delay(800L)
        navnController.navigate("start")
    }
    Box(modifier = Modifier.fillMaxSize()){
        Image(painterResource(id = R.drawable.logobag), contentDescription = "logo", modifier = Modifier.align(
            Alignment.Center))
    }
}
