/*
 * Copyright (c) 2021. Erlend Tyrmi
 */

package com.erlend.cryptomall.presentation.ui.composables.trade

import androidx.compose.foundation.layout.Box
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun TradeTopBar(){
    Box {
        Text(text = "Top bar for account pages!", modifier = Modifier.align(Alignment.Center))
    }
}