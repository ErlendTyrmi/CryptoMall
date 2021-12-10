/*
 * Copyright (c) 2021. Erlend Tyrmi
 */

package com.erlend.cryptomall.view.ui.composables.asset

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.erlend.cryptomall.view.viewModels.AssetViewModel
import com.erlend.cryptomall.view.viewModels.AccountViewModel

// This is subpage of overview!

@Composable
fun Portfolio(assetModel: AssetViewModel, navController: NavHostController, accountViewModel: AccountViewModel){

    val assets by accountViewModel.ownedAssets.observeAsState()

    Column {
        AccountTopBar(accountViewModel, navController)
        Box(modifier = Modifier.fillMaxWidth()){
            assets?.let {
                LazyColumn {
                    items(it) { ass ->
                        Row(modifier = Modifier.padding(16.dp, 0.dp)) {
                            Text(modifier = Modifier.weight(1f), text = ass.assetSymbol)
                            Text(modifier = Modifier.weight(2f), text = ass.amountOwned.take(7))
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
        Button(onClick = { navController.navigate("transactions") }) {
            Text("Transaction history")
        }
    }
}