/*
 * Copyright (c) 2021. Erlend Tyrmi
 */

package com.erlend.cryptomall.view.ui.composables.trade

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.erlend.cryptomall.view.viewModels.TradeViewModel

// Icon name symbol (abbrev. eg. BTC) rate in header
// Owned amount and dollar value of this
// Buy, sell
// Optional: Graph of fluctuations last 24h/week

@Composable
fun Currency(navController: NavHostController, tradeModel: TradeViewModel, symbol: String) {

    Box(modifier = Modifier.fillMaxSize()){
        Text(text = "$symbol here!", modifier = Modifier.align(Alignment.Center))
    }
}