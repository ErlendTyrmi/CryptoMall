/*
 * Copyright (c) 2021. Erlend Tyrmi
 */

package com.erlend.cryptomall.presentation.ui.composables.account

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.erlend.cryptomall.presentation.viewModels.AssetViewModel

// This page shows the "points" (value in dollars) at the top, and a list of currencies.
// The currencies have present value in $ and change % last 24h.
// Click on points to see portfolio
// Click currency to see "Currency"
// Icons @ https://static.coincap.io/assets/icons/btc@2x.png

@Composable
fun Overview(assetModel: AssetViewModel){
    Column {
        AccountTopBar()
        Box(modifier = Modifier.fillMaxSize()){
            Text(text = "Overview here!", modifier = Modifier.align(Alignment.Center))
        }
        LazyColumn(){

        }
    }
}

fun getAssets(){

}