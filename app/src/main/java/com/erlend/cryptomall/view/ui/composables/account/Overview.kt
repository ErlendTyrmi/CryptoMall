/*
 * Copyright (c) 2021. Erlend Tyrmi
 */

package com.erlend.cryptomall.view.ui.composables.account

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.erlend.cryptomall.domain.model.entities.Asset
import com.erlend.cryptomall.view.viewModels.AssetViewModel

// This page shows the "points" (value in dollars) at the top, and a list of currencies.
// The currencies have present value in $ and change % last 24h.
// Click on points to see portfolio
// Click currency to see "Currency"
// Icons @ https://static.coincap.io/assets/icons/btc@2x.png

@ExperimentalComposeUiApi
@Composable
fun Overview(navController: NavHostController, assetViewModel: AssetViewModel) {

    val assets by assetViewModel.getAssetsLocal().observeAsState()
    val query by assetViewModel.assetsQuery

    // onClick for the asset list, taking symbol as arg
    val onClickAsset: (String) -> Unit = {
        navController.navigate("currency/$it")
    }

    // DEBUG
    // Log.d("overview", assets.toString())

    Column {
        AccountTopBar()
        AssetSearchBar(assetViewModel = assetViewModel)
        Box(modifier = Modifier.fillMaxSize()) {
            MessageList(
                assets!!.filter { it.name.contains(query, ignoreCase = true) || it.name.contains(query, ignoreCase = true)|| query == ""},
                onClickAsset
            )
        }
    }
}

@Composable
fun MessageList(assets: List<Asset>, onClick: (String) -> Unit) {

    LazyColumn {
        items(assets) { ass ->
            AssetListCard(ass, onClick)
        }
    }
}
