/*
 * Copyright (c) 2021. Erlend Tyrmi
 */

package com.erlend.cryptomall.view.ui.composables.trade

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.erlend.cryptomall.view.viewModels.TradeViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

// Icon name symbol (abbrev. eg. BTC) rate in header
// Owned amount and dollar value of this
// Buy, sell
// Optional: Graph of fluctuations last 24h/week

@Composable
fun Currency(navController: NavHostController, tradeViewModel: TradeViewModel, symbol: String) {

    val asset by tradeViewModel.getAssetLocal().observeAsState()
    tradeViewModel.pullAssetRemote(symbol)
    tradeViewModel.updateAssetLocal(symbol)
    tradeViewModel.updateOwnedAmount()

    Column() {
        TradeTopBar(asset)
        Column(modifier = Modifier.padding(16.dp).fillMaxHeight()) {
            Text("You own " + tradeViewModel.amountOwned.value, modifier = Modifier.fillMaxWidth().weight(4f))
            Row(modifier = Modifier.fillMaxWidth().weight(1f)) {
                Button(
                    modifier = Modifier.weight(1F).padding(16.dp),
                    onClick = { navController.navigate("buy/$symbol") }
                ) {
                    Text(text = "Buy ${asset?.name}")
                }
                Button(
                    modifier = Modifier.weight(1F).padding(16.dp),
                    onClick = { navController.navigate("sell/$symbol") }
                ) {
                    Text(text = "Sell ${asset?.name}")
                }
            }

        }
    }


}