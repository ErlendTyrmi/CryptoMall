package com.erlend.cryptomall.ui.pages

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

// Show all transactions made, date bought for $/ sold for $,
// Subpage of overview

@Composable
fun Transactions(){
    Box(modifier = Modifier.fillMaxSize()){
        Text(text = "Transactions here!", modifier = Modifier.align(Alignment.Center))
    }
}