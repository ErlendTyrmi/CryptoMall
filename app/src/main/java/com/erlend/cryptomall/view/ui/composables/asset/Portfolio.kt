/*
 * Copyright (c) 2021. Erlend Tyrmi
 */

package com.erlend.cryptomall.view.ui.composables.asset

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Bottom
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.erlend.cryptomall.view.ui.composables.BackHandler
import com.erlend.cryptomall.view.viewModels.AssetViewModel
import com.erlend.cryptomall.view.viewModels.AccountViewModel

// This is subpage of overview!

@Composable
fun Portfolio(assetModel: AssetViewModel, navController: NavHostController, accountViewModel: AccountViewModel){

    val assets by accountViewModel.ownedAssets.observeAsState()

    // Custom Back Button nav
    BackHandler(onBack={
        navController.navigate("overview")
    })

    // onClick for the portfolio asset list, taking symbol as arg
    val onClickAsset: (String) -> Unit = {
        navController.navigate("currency/$it")
    }

    Column {
        AccountTopBar(accountViewModel, navController)
        Box(modifier = Modifier
            .align(CenterHorizontally)
            .padding(0.dp, 16.dp, 0.dp, 24.dp)
            ) {
            OutlinedButton(
                onClick = { navController.navigate("transactions") },

                ) {

                Text("See your transaction history")
            }
        }
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 0.dp)
           ){
            Row(modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 16.dp)) {
                Text(text = "Your assets",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.primaryVariant,
                    modifier = Modifier.weight(2f),
                )
                Text(text = "amount",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.primaryVariant,
                    modifier = Modifier.weight(3f),
                )
            }
            assets?.let {
                LazyColumn {
                    items(it) { ass ->
                        Row(Modifier.clickable(onClick = { onClickAsset(ass.assetSymbol) })) {
                            Text(modifier = Modifier.weight(2f), text = ass.assetSymbol)
                            Text(modifier = Modifier.weight(3f), text = ass.amountOwned.take(7))
                        }
                        Divider(
                            color = MaterialTheme.colors.secondary,
                            thickness = 1.dp,
                            modifier = Modifier.padding(0.dp, 16.dp),
                        )
                    }
                }
            }
        }
    }
}