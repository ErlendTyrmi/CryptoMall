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

// Enter an amount to buy as float, bounded by free dollars in portfolio
// Auto update price in dollars
// Subpage of currency

@Composable
fun Buy(){
    Box(modifier = Modifier.fillMaxSize()){
        Text(text = "Buy here!", modifier = Modifier.align(Alignment.Center))
    }
}