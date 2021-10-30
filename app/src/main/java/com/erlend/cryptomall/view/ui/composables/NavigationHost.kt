/*
 * Copyright (c) 2021. Erlend Tyrmi
 */

package com.erlend.cryptomall.view.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.erlend.cryptomall.view.ui.composables.*
import com.erlend.cryptomall.view.ui.composables.account.Overview
import com.erlend.cryptomall.view.viewModels.AssetViewModel
import com.erlend.cryptomall.view.viewModels.MainViewModel
import com.erlend.cryptomall.view.viewModels.TradeViewModel

@Composable
fun NavHost(
    navController: NavController,
    assetViewModel: AssetViewModel,
    tradeViewModel: TradeViewModel,
    startDestination: String = "overview",
    mainViewModel: MainViewModel
){

    NavHost(
        navController = navController as NavHostController, startDestination){
        composable(route = "splash"){
            Splash(navController = navController)
        }

        // Assets and account

        composable(route = "overview"){
            Overview(navController = navController,
                assetViewModel = assetViewModel)
        }
        composable(route = "portfolio"){
            Transactions(navController = navController,
                assetModel = assetViewModel)
        }
        composable(route = "transactions"){
            Portfolio(navController = navController,
                assetModel = assetViewModel)
        }

        // Trade

        composable(route = "currency/{symbol}"){
            Currency(navController = navController,
                tradeModel = tradeViewModel)
        }
        composable(route = "buy"){
            Buy(navController = navController,
                tradeModel = tradeViewModel)
        }
        composable(route = "sell"){
            Sell(navController = navController,
                tradeModel = tradeViewModel)
        }
    }
}