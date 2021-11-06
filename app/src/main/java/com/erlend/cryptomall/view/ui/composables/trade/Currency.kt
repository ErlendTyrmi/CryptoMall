/*
 * Copyright (c) 2021. Erlend Tyrmi
 */

package com.erlend.cryptomall.view.ui.composables.trade

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavHostController
import com.erlend.cryptomall.view.viewModels.TradeViewModel

// Icon name symbol (abbrev. eg. BTC) rate in header
// Owned amount and dollar value of this
// Buy, sell
// Optional: Graph of fluctuations last 24h/week

@Composable
fun Currency(navController: NavHostController, tradeViewModel: TradeViewModel, symbol: String) {

    val asset by tradeViewModel.getAssetLocal(symbol).observeAsState()
    tradeViewModel.pullAssetRemote(symbol)
    tradeViewModel.observeAsset(symbol)
Column() {
    TradeTopBar(asset)

    Column() {
        Text(text = "Buy shit")
        Button(onClick = { /*TODO*/ }){
            Text(text = "Buy ${asset?.name}")
            
        }
        Button(onClick = { /*TODO*/ }){
            Text(text = "Sell ${asset?.name}")

        }
    }
}






}