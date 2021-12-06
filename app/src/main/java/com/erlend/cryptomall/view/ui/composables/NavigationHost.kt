/*
 * Copyright (c) 2021. Erlend Tyrmi
 */

package com.erlend.cryptomall.view.ui.composables

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.erlend.cryptomall.view.ui.composables.asset.Overview
import com.erlend.cryptomall.view.ui.composables.asset.Portfolio
import com.erlend.cryptomall.view.ui.composables.asset.Transactions
import com.erlend.cryptomall.view.ui.composables.trade.Buy
import com.erlend.cryptomall.view.ui.composables.trade.Currency
import com.erlend.cryptomall.view.ui.composables.trade.Sell
import com.erlend.cryptomall.view.viewModels.AssetViewModel
import com.erlend.cryptomall.view.viewModels.AccountViewModel
import com.erlend.cryptomall.view.viewModels.TradeViewModel

@ExperimentalComposeUiApi
@Composable
fun NavHost(
    navController: NavController,
    startDestination: String = "overview",
    accountViewModel: AccountViewModel,
    assetViewModel: AssetViewModel,
    tradeViewModel: TradeViewModel, // TODO: Move navhost to Activity, Inject in navHost
) {

    NavHost(
        navController = navController as NavHostController, startDestination
    ) {
        composable(route = "splash") {
            Splash(navController = navController)
        }

        // Assets and account

        composable(route = "overview") {
            Overview(
                navController = navController,
                assetViewModel = assetViewModel,
                accountViewModel = accountViewModel
            )
        }
        composable(route = "portfolio") {
            Transactions(
                navController = navController,
                assetModel = assetViewModel,
                accountViewModel = accountViewModel
            )
        }
        composable(route = "transactions") {
            Portfolio(
                navController = navController,
                assetModel = assetViewModel,
                accountViewModel = accountViewModel
            )
        }

        // Trade

        composable(
            route = "Currency/{symbol}",
            // Path argument
            arguments = listOf(navArgument("symbol") { type = NavType.StringType })
        ) { // Path argument boiler plate
            backStackEntry ->
            backStackEntry.arguments?.getString("symbol")?.let {
                Currency(
                    navController = navController,
                    tradeViewModel = tradeViewModel,
                    it
                )
            }
        }
            composable(route = "buy") {
                Buy(
                    navController = navController,
                    tradeModel = tradeViewModel
                )
            }
            composable(route = "sell") {
                Sell(
                    navController = navController,
                    tradeModel = tradeViewModel
                )
            }
        }
    }