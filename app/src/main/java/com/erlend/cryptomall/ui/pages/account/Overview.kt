package com.erlend.cryptomall.ui.pages

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

// This page shows the "points" (value in dollars) at the top, and a list of currencies.
// The currencies have present value in $ and change % last 24h.
// Click on points to see portfolio
// Click currency to see "Currency"

@Composable
fun Overview(){
    Box(modifier = Modifier.fillMaxSize()){
        Text(text = "Overview here!", modifier = Modifier.align(Alignment.Center))
    }
}