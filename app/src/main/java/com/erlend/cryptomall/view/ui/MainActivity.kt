/*
 * Copyright (c) 2021. Erlend Tyrmi
 */

package com.erlend.cryptomall.view.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.erlend.cryptomall.view.ui.composables.NavHost
import com.erlend.cryptomall.view.ui.composables.Splash
import com.erlend.cryptomall.view.ui.composables.asset.Overview
import com.erlend.cryptomall.view.ui.composables.asset.Portfolio
import com.erlend.cryptomall.view.ui.composables.asset.Transactions
import com.erlend.cryptomall.view.ui.composables.trade.Buy
import com.erlend.cryptomall.view.ui.composables.trade.Currency
import com.erlend.cryptomall.view.ui.composables.trade.Sell

import com.erlend.cryptomall.view.ui.theme.CryptoMallTheme
import com.erlend.cryptomall.view.viewModels.AssetViewModel
import com.erlend.cryptomall.view.viewModels.AccountViewModel
import com.erlend.cryptomall.view.viewModels.TradeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val assetModel: AssetViewModel by viewModels()
    private val tradeModel: TradeViewModel by viewModels()

    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            val startDestination = "splash"
            CryptoMallTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    androidx.navigation.compose.NavHost(
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
                                accountViewModel = AccountViewModel by viewModels()
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
            }
        }
    }
}


