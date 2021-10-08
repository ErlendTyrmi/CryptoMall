package com.erlend.cryptomall.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.erlend.cryptomall.ui.pages.Overview
import com.erlend.cryptomall.ui.pages.Splash

@Composable
fun Navigation(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "splash"){
        composable(route = "splash"){
            Splash(navController = navController)
        }
        composable(route = "start"){
            Overview()
        }
    }
}