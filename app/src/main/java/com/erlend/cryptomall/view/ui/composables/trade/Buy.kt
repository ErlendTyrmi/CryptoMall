/*
 * Copyright (c) 2021. Erlend Tyrmi
 */

package com.erlend.cryptomall.view.ui.composables.trade

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.erlend.cryptomall.view.ui.composables.asset.AccountTopBar
import com.erlend.cryptomall.view.viewModels.AccountViewModel
import com.erlend.cryptomall.view.viewModels.TradeViewModel

// Enter an amount to buy as float, bounded by free dollars in portfolio
// Auto update price in dollars
// Subpage of currency

@Composable
fun Buy(navController: NavHostController, tradeViewModel: TradeViewModel, symbol: String) {

    val asset by tradeViewModel.getAssetLocal().observeAsState()
    tradeViewModel.pullAssetRemote(symbol)
    tradeViewModel.observeAssetLocal(symbol)

    var text by remember { mutableStateOf("") }

    Column() {
        TradeTopBar(asset = asset)
        Box(modifier = Modifier.fillMaxSize()){
            Column(modifier = Modifier
                .align(Alignment.Center)
                .padding(16.dp, 0.dp)){

                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 16.dp),
                    value = text,
                    onValueChange = { text = it },
                    label = { Text("Enter amount")},
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 16.dp),
                    onClick = { navController.navigate("buy/$symbol") }
                ) {
                    Text(text = "Buy ${asset?.name}", Modifier.padding(8.dp))
                }
            }
        }
    }
}