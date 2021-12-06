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

    private val accountModel: AccountViewModel by viewModels()
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
                    NavHost(
                        navController = navController,
                        startDestination = startDestination,
                        accountViewModel = accountModel,
                        assetViewModel = assetModel,
                        tradeViewModel = tradeModel,
                    )
                }
            }
        }
    }
}


