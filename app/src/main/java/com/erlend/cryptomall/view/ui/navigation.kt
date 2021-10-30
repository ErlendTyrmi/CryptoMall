/*
 * Copyright (c) 2021. Erlend Tyrmi
 */

package com.erlend.cryptomall.view.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.erlend.cryptomall.view.ui.composables.account.Overview
import com.erlend.cryptomall.view.ui.composables.Splash
import com.erlend.cryptomall.view.viewModels.AssetViewModel

@Composable
fun Navigation(assetModel: AssetViewModel){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "splash"){
        composable(route = "splash"){
            Splash(navController = navController)
        }
        composable(route = "start"){
            Overview(assetModel = assetModel)
        }
    }
}