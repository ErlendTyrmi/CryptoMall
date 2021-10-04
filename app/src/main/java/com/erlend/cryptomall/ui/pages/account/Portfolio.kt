package com.erlend.cryptomall.ui.pages

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

// User points in header (copy from Overview!)
// How user points are calculated
// List of all assets (cryptos an $)
// A "Transactions link"

// This is subpage of overview!

@Composable
fun Portfolio(){
    Box(modifier = Modifier.fillMaxSize()){
        Text(text = "Portfolio here!", modifier = Modifier.align(Alignment.Center))
    }
}