/*
 * Copyright (c) 2021. Erlend Tyrmi
 */

package com.erlend.cryptomall.presentation.ui.pages

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

// Icon name symbol (abbrev. eg. BTC) rate in header
// Owned amount and dollar value of this
// Buy, sell
// Optional: Graph of fluctuations last 24h/week

@Composable
fun Currency(){
    Box(modifier = Modifier.fillMaxSize()){
        Text(text = "Currency here!", modifier = Modifier.align(Alignment.Center))
    }
}