/*
 * Copyright (c) 2021. Erlend Tyrmi
 */

package com.erlend.cryptomall.view.ui.composables.trade

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavHostController
import com.erlend.cryptomall.view.ui.composables.BackHandler
import com.erlend.cryptomall.view.viewModels.TradeViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

// Icon name symbol (abbrev. eg. BTC) rate in header
// Owned amount and dollar value of this
// Buy, sell
// Optional: Graph of fluctuations last 24h/week

@ExperimentalComposeUiApi
@Composable
fun Currency(navController: NavHostController, tradeViewModel: TradeViewModel, symbol: String) {

    val asset by tradeViewModel.getAssetLocal().observeAsState()
    val amountOwned by tradeViewModel.amountOwned.observeAsState("0")

    // Show/hide buy sell menus
    val showBuy = remember { mutableStateOf(false) }
    val showSell = remember { mutableStateOf(false) }


    tradeViewModel.pullAssetRemote(symbol)
    tradeViewModel.updateAssetLocal(symbol)
    tradeViewModel.updateOwnedAmount()

    // Custom Back Button nav
    BackHandler(onBack={
        navController.navigate("overview")
    })

    Column() {
        TradeTopBar(asset)
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxHeight()
        ) {
            Text(
                "You own $amountOwned", modifier = Modifier
                    .fillMaxWidth()
                    .weight(4f)
            )
            // Show buy and sell here instead of navigating
            if (showBuy.value && ! showSell.value)
                Buy(tradeViewModel, symbol)

            if (showSell.value && ! showBuy.value)
                Sell(tradeViewModel, symbol)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Button(
                    enabled = !showBuy.value,
                    modifier = Modifier
                        .weight(1F)
                        .padding(16.dp),
                    onClick = {
                        showBuy.value = true //navController.navigate("buy/$symbol")
                        showSell.value = false
                    }
                ) {
                    Text(text = "Buy ${asset?.name}")
                }
                Button(
                    enabled = !showSell.value,
                    modifier = Modifier
                        .weight(1F)
                        .padding(16.dp),
                    onClick = {
                        showSell.value = true
                        showBuy.value = false
                        //navController.navigate("sell/$symbol")
                    }
                ) {
                    Text(text = "Sell ${asset?.name}")
                }
            }
        }

    }
}