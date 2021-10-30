/*
 * Copyright (c) 2021. Erlend Tyrmi
 */

package com.erlend.cryptomall.view.ui.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.erlend.cryptomall.view.viewModels.TradeViewModel

// Sell button, anter a float to sell
// Dollars earned auto update
// Subpage of Currency

@Composable
fun Sell(navController: NavHostController, tradeModel: TradeViewModel) {
    Box(modifier = Modifier.fillMaxSize()){
        Text(text = "Currency here!", modifier = Modifier.align(Alignment.Center))
    }
}