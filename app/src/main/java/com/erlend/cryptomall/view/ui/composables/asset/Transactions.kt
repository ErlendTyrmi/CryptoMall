/*
 * Copyright (c) 2021. Erlend Tyrmi
 */

package com.erlend.cryptomall.view.ui.composables.asset

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.erlend.cryptomall.view.ui.composables.BackHandler
import com.erlend.cryptomall.view.viewModels.AssetViewModel
import com.erlend.cryptomall.view.viewModels.AccountViewModel
import java.nio.file.Files.size
import java.util.*

// Show all transactions made, date bought for $/ sold for $,
// Subpage of overview

@Composable
fun Transactions(navController: NavHostController, assetModel: AssetViewModel, accountViewModel: AccountViewModel) {

    val transactions by accountViewModel.transactions.observeAsState()
    accountViewModel.updateTransactions()

    // Custom Back Button nav
    BackHandler(onBack={
        navController.navigate("portfolio")
    })

    Column {
        AccountTopBar(accountViewModel, navController)

        Column(modifier = Modifier.fillMaxWidth().padding(16.dp, 0.dp)){

            Text(text = "Your transactions",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.primaryVariant,
                modifier = Modifier.padding(0.dp, 16.dp),
            )

            transactions?.reversed()?.let {
                LazyColumn {
                    items(it) { trans ->

                        Column() {
                            Text(text = "${Date(trans.timestamp)}", fontSize = 12.sp, color = MaterialTheme.colors.primaryVariant)
                            Text(text = "Bought ${trans.inAmount.take(8)} ${trans.inCurrencySymbol}")
                            Text("You paid ${trans.outAmount.take(5)} ${trans.outCurrencySymbol}")
                        }

                        Divider(
                            color = MaterialTheme.colors.secondary,
                            thickness = 1.dp,
                            modifier = Modifier.padding(16.dp),
                        )
                    }
                }
            }
        }
    }
}