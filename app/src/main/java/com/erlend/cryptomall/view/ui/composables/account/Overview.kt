/*
 * Copyright (c) 2021. Erlend Tyrmi
 */

package com.erlend.cryptomall.view.ui.composables.account

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.erlend.cryptomall.domain.model.entities.Asset
import com.erlend.cryptomall.view.viewModels.AssetViewModel

// This page shows the "points" (value in dollars) at the top, and a list of currencies.
// The currencies have present value in $ and change % last 24h.
// Click on points to see portfolio
// Click currency to see "Currency"
// Icons @ https://static.coincap.io/assets/icons/btc@2x.png

@Composable
fun Overview(assetModel: AssetViewModel) {

    val assets by assetModel.getAssetsLocal().observeAsState()

    // DEBUG
    // Log.d("overview", assets.toString())

    Column {
        AccountTopBar()
        Box(modifier = Modifier.fillMaxSize()) {
            MessageList(assets!!)
        }

    }
}

@Composable
fun MessageList(assets: List<Asset>) {
    LazyColumn {
        items(assets) { ass ->
            AssetListCard(ass)
        }
    }
}