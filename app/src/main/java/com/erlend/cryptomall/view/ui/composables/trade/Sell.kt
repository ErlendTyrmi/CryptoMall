/*
 * Copyright (c) 2021. Erlend Tyrmi
 */

package com.erlend.cryptomall.view.ui.composables.trade

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.erlend.cryptomall.view.viewModels.TradeViewModel

// Sell button, anter a float to sell
// Dollars earned auto update
// Subpage of Currency

@ExperimentalComposeUiApi
@Composable
fun Sell(tradeViewModel: TradeViewModel, symbol: String) {

    // Get keyboardController to allow closing the keyboard
    val keyboardController = LocalSoftwareKeyboardController.current


    // Update on load (once)
    LaunchedEffect(Unit){
        tradeViewModel.pullAssetRemote(symbol)
        tradeViewModel.updateAssetLocal(symbol)
        tradeViewModel.updateDollarsOwned()
        tradeViewModel.updateOwnedAmount()
    }

    var amountText by remember { mutableStateOf("") }
    val openDialog = remember { mutableStateOf(false) }
    val openError = remember { mutableStateOf(false) }

    Column() {
        Box(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(16.dp, 0.dp)
            ) {

                if (openDialog.value) {
                    AlertDialog(
                        onDismissRequest = { openDialog.value = false },
                        title = { Text(text = "Sell $symbol") },
                        text = { Text("Are you sure you want to sell $amountText $symbol and get USD ${tradeViewModel.getCurrentTotalPrice(amountText)}?") },
                        confirmButton = {
                            Button( onClick = {
                                tradeViewModel.sell(symbol, amountText)
                                openDialog.value = false
                                //navController.navigate("currency/$symbol")
                            }) {
                                Text("Sell")
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
                        text = { Text("You don't have enough crypto to sell.") },
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
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(
                        onDone = {keyboardController?.hide()})
                )

                Button(
                    enabled = tradeViewModel.checkOwnedAssetAmountMoreThanOrEqual(amountText),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 16.dp),
                    onClick = {
                        if (tradeViewModel.checkOwnedAssetAmountMoreThanOrEqual(amountText)) {
                            openDialog.value = true
                        } else {
                            // In case the button is not disabled
                            openError.value = true
                        }
                    }
                ) {
                    Text(text = "SELL", Modifier.padding(8.dp))
                }
            }
        }
    }
}