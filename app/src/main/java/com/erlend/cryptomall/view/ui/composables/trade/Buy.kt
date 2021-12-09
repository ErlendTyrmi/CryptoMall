/*
 * Copyright (c) 2021. Erlend Tyrmi
 */

package com.erlend.cryptomall.view.ui.composables.trade

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
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
import com.erlend.cryptomall.view.viewModels.TradeViewModel

// Enter an amount to buy as float, bounded by free dollars in portfolio
// Auto update price in dollars
// Subpage of currency

const val TAG = "Buy"

@Composable
fun Buy(navController: NavHostController, tradeViewModel: TradeViewModel, symbol: String) {

    val asset by tradeViewModel.getAssetLocal().observeAsState()
    tradeViewModel.pullAssetRemote(symbol)
    tradeViewModel.updateAssetLocal(symbol)
    tradeViewModel.updateDollarsOwned()

    var amountText by remember { mutableStateOf("") }
    val openDialog = remember { mutableStateOf(false) }
    val openError = remember { mutableStateOf(false) }


    Column() {
        TradeTopBar(asset = asset)
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(16.dp, 0.dp)
            ) {

                if (openDialog.value) {
                    AlertDialog(
                        onDismissRequest = { openDialog.value = false },
                        title = { Text(text = "Buy $symbol") },
                        text = { Text("Are you sure you want to buy $amountText $symbol?") },
                        confirmButton = {
                            Button( onClick = {
                                    tradeViewModel.buy(symbol, amountText)
                                    openDialog.value = false
                                    navController.navigate("currency/$symbol")
                                }) {
                                Text("Buy")
                            }
                        },
                        dismissButton = { Button(
                                onClick = { openDialog.value = false }) {
                                Text("Cancel")
                            }
                        }
                    )
                }

                if (openError.value) {
                    AlertDialog(
                        onDismissRequest = {
                            openError.value = false
                        },
                        title = { Text(text = "Buy $symbol") },
                        text = { Text("You don't have that kind of cash.") },
                        confirmButton = { Button( onClick = { openError.value = false }) {
                                Text("OK")
                            }
                        },
                    )
                }

                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 16.dp),
                    value = amountText,
                    onValueChange = { amountText = it },
                    label = { Text("Enter amount") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )

                Button(
                    enabled = tradeViewModel.checkDraft(amountText),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 16.dp),
                    onClick = {
                        if (tradeViewModel.checkDraft(amountText)) {
                            openDialog.value = true
                        } else {
                            // In case the button is not disabled
                            openError.value = true
                        }
                    }
                ) {
                    Text(text = "Buy ${asset?.name}", Modifier.padding(8.dp))
                }
            }
        }
    }
}