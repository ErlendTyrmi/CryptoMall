/*
 * Copyright (c) 2021. Erlend Tyrmi
 */

package com.erlend.cryptomall.presentation.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.erlend.cryptomall.presentation.ui.pages.account.Overview
import com.erlend.cryptomall.presentation.ui.pages.Splash
import com.erlend.cryptomall.presentation.viewModels.AssetViewModel

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