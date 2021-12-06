/*
 * Copyright (c) 2021. Erlend Tyrmi
 */

package com.erlend.cryptomall.view.ui.composables.asset

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.erlend.cryptomall.view.viewModels.AssetViewModel
import com.erlend.cryptomall.view.viewModels.AccountViewModel

// Show all transactions made, date bought for $/ sold for $,
// Subpage of overview

@Composable
fun Transactions(navController: NavHostController, assetModel: AssetViewModel, accountViewModel: AccountViewModel) {
    Column {
        AccountTopBar(accountViewModel)
        Box(modifier = Modifier.fillMaxSize()){
            Text(text = "Transactions here!", modifier = Modifier.align(Alignment.Center))
        }
    }
}